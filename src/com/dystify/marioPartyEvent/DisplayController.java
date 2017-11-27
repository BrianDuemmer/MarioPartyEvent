package com.dystify.marioPartyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.control2.command.RollCommand;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.graphic.GameBoard;
import com.dystify.marioPartyEvent.graphic.Place;
import com.dystify.marioPartyEvent.graphic.Player;
import com.dystify.marioPartyEvent.graphic.ScoreBoard;
import com.dystify.marioPartyEvent.graphic.SpriteState;
import com.dystify.marioPartyEvent.minigame.AbstractMinigame;
import com.dystify.marioPartyEvent.minigame.CurtainCall;
import com.dystify.marioPartyEvent.minigame.EndOfTheLine;
import com.dystify.marioPartyEvent.minigame.GuessTheQuote;
import com.dystify.marioPartyEvent.minigame.LookAway;
import com.dystify.marioPartyEvent.minigame.MerryGoChomp;
import com.dystify.marioPartyEvent.minigame.PoundPeril;
import com.dystify.marioPartyEvent.minigame.RoleCall;
import com.dystify.marioPartyEvent.minigame.RollOfTheDiceMulti;
import com.dystify.marioPartyEvent.minigame.RollOfTheDiceSolo;
import com.dystify.marioPartyEvent.minigame.Slots;
import com.dystify.marioPartyEvent.minigame.SpotTheDifference;
import com.dystify.marioPartyEvent.minigame.ToadSays;
import com.dystify.marioPartyEvent.minigame.TreasurePick;
import com.dystify.marioPartyEvent.space.AbstractSpace;
import com.dystify.marioPartyEvent.space.MushroomSpace;
import com.dystify.marioPartyEvent.util.CircularLinkedList;
import com.dystify.marioPartyEvent.util.PathBranchHandler;
import com.dystify.marioPartyEvent.util.Util;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;



public class DisplayController 
{
	// for moving the scene with the mouse
	private double xOffset;
	private double yOffest;

	private StackPane rootPane;
	private GameBoard board;

	private Stage stage;

	private Player mario;
	private Player luigi;
	private Player peach;
	private Player yoshi;

	private Player bowser;
	private Player toad;

	private TextArea textBox;
	private ImageView fadeOutImg;

	private boolean endNextTurn = false;

	private CircularLinkedList<AbstractMinigame> soloMinigames;
	private CircularLinkedList<AbstractMinigame> fourPlayerMiniGames;
	private CircularLinkedList<AbstractMinigame> BowserMiniGames;


	/**
	 * Standard handler to use when encountering a branch
	 */
	public PathBranchHandler defaultBranchHandler = (AbstractSpace s, Player p) -> {
		String reg = s.branchDir(board.getSpaces().get(s.getNextSpace())).trim();
		String branch = s.branchDir(board.getSpaces().get(s.getBranchNext())).trim();

		// poll chat
		setDialogText(String.format("Which direction should %s go?\nTeam %s, choose %s or %s !", p.getName(), p.getName(), reg, branch), false, 5000);
		String resp = CommandReader.inst().voteOneTeam(p, 
				Filter.mergeFilters(
						Filter.dirStringToFilter(reg), 
						Filter.dirStringToFilter(branch)), 
				15000);

		boolean willBranch = resp.equalsIgnoreCase(branch);

		if(!willBranch)
			resp = reg; // don't assume that the value returned is even valid. Just be sure it's the default

		setDialogText(String.format("Looks like %s will go %s!", p.getName(), resp.replaceAll("!", "")), false, 5000);
		return willBranch;
		//		return Math.random() > 0.5;
	};




	public DisplayController() 
	{
		// init game board
		try { board = new GameBoard("yoshi"); } 
		catch (IOException e1) {
			System.err.println("failed to init board!");
			e1.printStackTrace();
		}

		// init the main window
		rootPane = new StackPane();
		rootPane.setAlignment(Pos.TOP_LEFT);
		rootPane.getChildren().add(board.getBackgroundNode());
		Scene scene = new Scene(rootPane);
		scene.getStylesheets().add(Util.loadFile("/common/game_window.css").toExternalForm());
		stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);

		initPlayers();

		// set these to allow dragging and dropping the window
		rootPane.setOnMousePressed((MouseEvent e) -> {
			xOffset = e.getSceneX();
			yOffest = e.getSceneY();
		});

		rootPane.setOnMouseDragged((MouseEvent e) -> {
			stage.setX(e.getScreenX() - xOffset);
			stage.setY(e.getScreenY() - yOffest);
		}); 

		// setup dialog box
		textBox = new TextArea();
		StackPane.setAlignment(textBox, Pos.TOP_CENTER);
		textBox.setEditable(false);
		textBox.setMaxWidth(800);
		textBox.setMaxHeight(200);

		fadeOutImg = new ImageView(Util.loadFile("/common/background.png").toExternalForm());
		fadeOutImg.setOpacity(1);

		rootPane.getChildren().addAll(fadeOutImg, textBox);

		initMinigames();
	}




	private void initMinigames() {
		// four player
		fourPlayerMiniGames = new CircularLinkedList<>();
		fourPlayerMiniGames.add(new EndOfTheLine());
		fourPlayerMiniGames.add(new ToadSays());
		fourPlayerMiniGames.add(new RoleCall());
		fourPlayerMiniGames.add(new RollOfTheDiceMulti());
		fourPlayerMiniGames.add(new LookAway());
		fourPlayerMiniGames.add(new CurtainCall());
		fourPlayerMiniGames.add(new GuessTheQuote());
		fourPlayerMiniGames.add(new SpotTheDifference());
		fourPlayerMiniGames.add(new MerryGoChomp());

		// solo
		soloMinigames = new CircularLinkedList<>();
		soloMinigames.add(new RollOfTheDiceSolo());
		soloMinigames.add(new Slots());
		soloMinigames.add(new TreasurePick());
		soloMinigames.add(new Slots());
		soloMinigames.add(new RollOfTheDiceSolo());
		soloMinigames.add(new TreasurePick());

		//bowser
		//idfk what even the deal with these is
		BowserMiniGames = new CircularLinkedList<>();
	}





	/**
	 * applies text to the dialog box 
	 * @param text
	 * @param append
	 */
	public void setDialogText(String text, boolean append, int timeToClear)
	{
		System.out.print(text);
		if(!append)
			System.out.println();

		if(!text.trim().isEmpty()) {
			Thread t = new Thread(() -> 
			{
				synchronized (textBox) 
				{
					if(!append) // clear the text
						Platform.runLater(()->{ textBox.setText(""); });

					char[] chars = text.toCharArray();
					for(char c : chars) {
						Platform.runLater(()-> { textBox.appendText(String.valueOf(c)); });
						try { Thread.sleep(30); } catch (InterruptedException e) {}
					}

					if(timeToClear > 0 ) {
						try { Thread.sleep(timeToClear); } catch (InterruptedException e) {}
						Platform.runLater(()-> { textBox.setText(""); });
					}
				}
			});
			t.setDaemon(true);
			t.start();
		}
	}






	public void testMinigame() {
		new Thread(()->{
			AbstractMinigame m = /*new EndOfTheLine(); new TreasurePick();  new RollOfTheDiceMulti();*/ new MerryGoChomp();

			List<Player> g = new ArrayList<>();
			g.add(mario);
			g.add(luigi);
			g.add(peach);
			g.add(yoshi);

			m.playGame(g, this);
		}).start();
	}
	
	
	/**
	 * Tests every single text demo sequentially
	 */
	public void testTextDemos() 
	{
		new Thread(() -> {
			for(AbstractMinigame p : fourPlayerMiniGames) {
				System.err.println(p.getName());
				p.tempTestTxtDemo(this);
			}
			
			for(AbstractMinigame p : soloMinigames) {
				System.err.println(p.getName());
				p.tempTestTxtDemo(this);
			}
			
			for(AbstractMinigame p : BowserMiniGames) {
				System.err.println(p.getName());
				p.tempTestTxtDemo(this);
			}
		}).start();
	}






	/**
	 * Calculates the ranks for each player and applies the graphical transforms
	 */
	public void calcRanks() {

		// sort the players in descending order
		List<SortablePlayer> players = new ArrayList<SortablePlayer>(4);
		players.add(new SortablePlayer(luigi.getNumStars()*10000 + luigi.getNumCoins(), luigi));
		players.add(new SortablePlayer(mario.getNumStars()*10000 + mario.getNumCoins(), mario));
		players.add(new SortablePlayer(peach.getNumStars()*10000 + peach.getNumCoins(), peach));
		players.add(new SortablePlayer(yoshi.getNumStars()*10000 + yoshi.getNumCoins(), yoshi));
		Collections.sort(players);
		Collections.reverse(players);

		// whoever's first in the list must be first overall
		int lastHighestVal = players.get(0).rollVal;
		int lastHighestPalce = 1;
		players.get(0).player.setPlace(Place.FIRST);

		// we can't assume the levels of the others
		for(int i=1; i<players.size(); i++) {
			SortablePlayer p = players.get(i);
			if(p.rollVal < lastHighestVal) {
				lastHighestVal = p.rollVal;
				lastHighestPalce++;
			}
			p.player.setPlace(Place.fromNum(lastHighestPalce));
		}
	}






	public void swapBowserToad() 
	{
		int xDiff = bowser.getCurrX() - toad.getCurrX();
		Player startsOnLeft = xDiff > 0 ? toad : bowser;
		Player startsOnRight = xDiff < 0 ? toad : bowser;

		startsOnLeft.walk(-40, startsOnLeft.getCurrY());
		startsOnRight.walk((int) (stage.getWidth() + 40), startsOnRight.getCurrY());

		startsOnLeft.setSpritePos((int) (stage.getWidth() + 40), 420);
		startsOnRight.setSpritePos(-40, 510);

		startsOnLeft.walk(1125, 420);
		Platform.runLater(() ->{ startsOnLeft.setSprite(SpriteState.STILL_LEFT); });

		startsOnRight.walk(25, 510);
		Platform.runLater(() ->{ startsOnRight.setSprite(SpriteState.STILL_RIGHT); });


	}








	public void show() { stage.show(); }
	public void hide() { stage.hide(); }
	//////////////////////////////////////// test methods ////////////////////////////////////////////



	private class SortablePlayer implements Comparable<SortablePlayer> {
		public int rollVal;
		public Player player;

		public SortablePlayer(int rollVal, Player player) {
			this.rollVal = rollVal;
			this.player = player;
		}

		@Override public int compareTo(SortablePlayer arg0) { return this.rollVal-arg0.rollVal; }
	}





	public void TurnSequencer() 
	{
		Thread t = new Thread(()->{
			setDialogText("Roll for each character, to\ndetermine the turn order!", false, -1);
			
			// sort them by their dice rolls
			List<SortablePlayer> sortablePlayer = new ArrayList<>();
			Map<Player, RollCommand>  rolls = CommandReader.inst().pollAllForCommand(Arrays.asList(luigi, mario, peach, yoshi), new RollCommand(0), -1);
			for(Entry<Player, RollCommand> e : rolls.entrySet())
				sortablePlayer.add(new SortablePlayer(e.getValue().getRollNum(), e.getKey()));

			Collections.sort(sortablePlayer);
			Collections.reverse(sortablePlayer);

			List<Player> players = new ArrayList<>(4);
			players.add(sortablePlayer.get(0).player);
			players.add(sortablePlayer.get(1).player);
			players.add(sortablePlayer.get(2).player);
			players.add(sortablePlayer.get(3).player);

			// report on the order of the players
			StringBuilder playerList =new StringBuilder("This will be the play order:\n");
			for(int i=0; i<players.size(); i++)
			{
				playerList.append(players.get(i).getName());
				if(i == players.size()-2)
					playerList.append(", then ");
				else if(i != players.size()-1)
					playerList.append(", ");
			}

			setDialogText(playerList.toString(), false, 2000);

			
			// play the game, until it's flagged to stop
			while(true) {
				for(Player p : players) {
					orderPlayersLayers();
					setDialogText("Roll, " +p.getName()+ "!", false, -1);
					takeTurn(p);
				}
				if(endNextTurn)
					break;
				fourPlayerMiniGames.poll().playGame(players, this);
			}
			fadeTo(1, 1500);

			
			List<Player> winners = new ArrayList<>();
			for(Player p : players)
				if(p.getCurrRank() == Place.FIRST)
					winners.add(p);

			setDialogText("The game is over!\n", false, -1);
			try { Thread.sleep(2000); } catch(InterruptedException e) {}

			Player winner = luigi; // luigi is the default winner, because he's number 1.

			if(winners.size() > 1) {
				setDialogText("... But wait, there is a tie!", true, -1); try { Thread.sleep(2000); } catch(InterruptedException e) {}
				setDialogText("In that case...", false, -1); try { Thread.sleep(3000); } catch(InterruptedException e) {}
				AbstractMinigame tiebreaker = new PoundPeril();
				winners = tiebreaker.playGame(winners, this);
				if(winners.size() > 0) // let's not have an indexoutofbounds if we can avoid it
					winner = winners.get(0);
			} else {
				if(winners.size() > 0) // let's not have an indexoutofbounds if we can avoid it
					winner = winners.get(0);
				setDialogText("And The Superstar is...", true, -1); try { Thread.sleep(5000); } catch(InterruptedException e) {}
				setDialogText(winner.getName() + "!", false, -1); try { Thread.sleep(2000); } catch(InterruptedException e) {}
			}
			setDialogText("Congradulations to everyone on\n" +winner.getName()+ "'s Team!", false, -1); try { Thread.sleep(5000); } catch(InterruptedException e) {}
			setDialogText("And thank you everyone,\nfor playing the game!\n", false, -1); try { Thread.sleep(2000); } catch(InterruptedException e) {}
			setDialogText("We hope you enjoyed it!", true, -1); try { Thread.sleep(8000); } catch(InterruptedException e) {}

			//fade out the text for a nice ending
			Platform.runLater(() -> {
				FadeTransition trans = new FadeTransition(Duration.millis(5000), textBox);
				trans.setToValue(0);
				trans.play();
			});
		});

		t.setDaemon(true);
		t.setName("Game Thread");
		t.start();
	}
	
	
	
	/**
	 * orders the players to the correct layers, based on vertical position.
	 * Removes all player objects, orders them, and puts them back behind the last
	 * ScoreBoard object
	 */
	private void orderPlayersLayers() {
		Platform.runLater(()->{
			ObservableList<Node> allNodes = rootPane.getChildren();
			allNodes.removeAll(mario.getSprite(), luigi.getSprite(), peach.getSprite(), yoshi.getSprite(), bowser.getSprite(), toad.getSprite());
			List<Player> players = Arrays.asList(mario, luigi, peach, yoshi, bowser, toad);
			
			Collections.sort(players, new Comparator<Player>() { // sort by the y-coordinate of the sprite. Higher y-coords should be on top, therefore have higher indexes
				@Override public int compare(Player o1, Player o2) {
					return o1.getCurrY() - o2.getCurrY();
				}
			});
			
			List<Node> plNodes = new ArrayList<>();
			for(Player p : players)
				plNodes.add(p.getSprite());
			
			int sbIdx = 0;
			for(; sbIdx<allNodes.size(); sbIdx++)
				if(allNodes.get(sbIdx) instanceof ScoreBoard)
					break;
			
			// add all of them in before any scoreboards
			allNodes.addAll(sbIdx, plNodes);
		});
	}




	private void initPlayers() {
		// init players
		luigi = new Player("Luigi", "/luigi", Color.RED);
		mario = new Player("Mario", "/mario", Color.CORNFLOWERBLUE);
		yoshi = new Player("Yoshi", "/yoshi", Color.GREEN);
		peach = new Player("Peach", "/peach", Color.GOLD);

		bowser = new Player("Bowser");
		bowser.initShadow("/bowser");
		bowser.setSprite(SpriteState.STILL_RIGHT);
		bowser.setSpritePos(25, 510);

		toad = new Player("Toad");
		toad.initShadow("/toad");
		toad.setSprite(SpriteState.STILL_LEFT);
		toad.setSpritePos(1125, 420);

		// setup the scoreboards
		luigi.setScoreboardPos(-405, -398);
		mario.setScoreboardPos(453, -398);
		yoshi.setScoreboardPos(-409, 381);
		peach.setScoreboardPos(451, 383);
		

		// add to the pane
		rootPane.getChildren().addAll(bowser.getSprite(), toad.getSprite(), yoshi.getSprite(), luigi.getSprite(), peach.getSprite(), mario.getSprite());
		rootPane.getChildren().addAll(luigi.getSb(), mario.getSb(), yoshi.getSb(), peach.getSb());
		
		// set everyone to starting spaces
		AbstractSpace start  = board.getSpaces().get(1);
		int x = start.getXPos();
		int y = start.getYPos();

		

		luigi.setCurrSpace(start);
		luigi.setSpritePos(x, y);

		mario.setCurrSpace(start);
		mario.setSpritePos(x, y);

		peach.setCurrSpace(start);
		peach.setSpritePos(x, y);

		yoshi.setCurrSpace(start);
		yoshi.setSpritePos(x, y);
		
		orderPlayersLayers();
	}






	/**
	 * gets the next dice roll and advances the player. Also handles all of the calls on 
	 * spaces as they are passed
	 * @param p the player to advance
	 */
	public void takeTurn(Player player) 
	{
		player.setActiveFace(true);
		int roll = player.getNextRoll();
		board.moveSpaces( player, roll, this, defaultBranchHandler);
		calcRanks();
		player.setActiveFace(false);
	}





	public GameBoard getBoard() {
		return board;
	}




	public void fadeTo(double opacity, int time) {
		Platform.runLater(() -> {
			FadeTransition t = new FadeTransition(Duration.millis(time), fadeOutImg);
			t.setToValue(opacity);
			t.play();
		});
	}





	public Player getMario() {
		return mario;
	}





	public Player getLuigi() {
		return luigi;
	}





	public Player getPeach() {
		return peach;
	}





	public Player getYoshi() {
		return yoshi;
	}




	public void setEndNextTurn(boolean endNextTurn) {
		this.endNextTurn = endNextTurn;
	}
	
	
	/**
	 * 
	 * @param advance if true, pulls the minigame out, to be played. If false, just peeks at it
	 * @return
	 */
	public AbstractMinigame getNext4pMinigame(boolean advance) {
		if(advance)
			return fourPlayerMiniGames.poll();
		else
			return fourPlayerMiniGames.peek();
	}
	
	/**
	 * 
	 * @param advance if true, pulls the minigame out, to be played. If false, just peeks at it
	 * @return
	 */
	public AbstractMinigame getNextspMinigame(boolean advance) {
		if(advance)
			return soloMinigames.poll();
		else
			return soloMinigames.peek();
	}
	
	
	/**
	 * 
	 * @param advance if true, pulls the minigame out, to be played. If false, just peeks at it
	 * @return
	 */
	public AbstractMinigame getNextBowserMinigame(boolean advance) {
		if(advance)
			return BowserMiniGames.poll();
		else
			return BowserMiniGames.peek();
	}




	public Stage getStage() {
		return stage;
	}

}





















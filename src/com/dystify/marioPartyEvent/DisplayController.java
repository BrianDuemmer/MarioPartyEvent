package com.dystify.marioPartyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dystify.marioPartyEvent.control.ChooseType;
import com.dystify.marioPartyEvent.control.Comm;
import com.dystify.marioPartyEvent.graphic.Place;
import com.dystify.marioPartyEvent.graphic.Player;
import com.dystify.marioPartyEvent.graphic.SpriteState;
import com.dystify.marioPartyEvent.minigame.AbstractMinigame;
import com.dystify.marioPartyEvent.minigame.EndOfTheLine;
import com.dystify.marioPartyEvent.minigame.TreasurePick;
import com.dystify.marioPartyEvent.space.AbstractSpace;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
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
	
	private List<AbstractMinigame> soloMinigames;
	private List<AbstractMinigame> fourPlayerMiniGames;
	private List<AbstractMinigame> BowserMiniGames;
	

	/**
	 * Standard handler to use when encountering a branch
	 */
	public PathBranchHandler defaultBranchHandler = (AbstractSpace s, Player p) -> {
		String reg = s.branchDir(board.getSpaces().get(s.getNextSpace())).trim();
		String branch = s.branchDir(board.getSpaces().get(s.getBranchNext())).trim();

		// poll chat
		setDialogText(String.format("Which direction should %s go?\nTeam %s, choose %s or %s!", p.getName(), p.getName(), reg, branch), false, 5000);
		String resp = Comm.getInst().getChoice(5000, ChooseType.DIRECTION, String.valueOf(p.getName().charAt(0))).trim();
		
		boolean willBranch = resp.equalsIgnoreCase(branch);
		
		if(!willBranch)
			resp = reg; // don't assume that the value returned is even valid. Just be sure it's the default
		
		setDialogText(String.format("Looks like %s will go %s!", p.getName(), resp), false, 5000);
		return willBranch;
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

		// init players
		luigi = new Player("Luigi", "/luigi", Color.RED);
		mario = new Player("Mario", "/mario", Color.CORNFLOWERBLUE);
		yoshi = new Player("Yoshi", "/yoshi", Color.GREEN);
		peach = new Player("Peach", "/peach", Color.GOLD);

		bowser = new Player("/bowser");
		bowser.setSprite(SpriteState.STILL_RIGHT);
		bowser.setSpritePos(141, 385);

		toad = new Player("/toad");
		toad.setSprite(SpriteState.STILL_LEFT);
		toad.setSpritePos(785, 342);

		// setup the scoreboards
		luigi.setScoreboardPos(-200, -270);
		mario.setScoreboardPos(200, -270);
		yoshi.setScoreboardPos(-200, 230);
		peach.setScoreboardPos(200, 230);

		// add to the pane
		rootPane.getChildren().addAll(bowser.getSprite(), toad.getSprite(), yoshi.getSprite(), luigi.getSprite(), peach.getSprite(), mario.getSprite());
		rootPane.getChildren().addAll(luigi.getSb(), mario.getSb(), yoshi.getSb(), peach.getSb());



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
		
		TurnSequencer();
	}





	/**
	 * applies text to the dialog box 
	 * @param text
	 * @param append
	 */
	public void setDialogText(String text, boolean append, int timeToClear)
	{
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
			AbstractMinigame m = /*new EndOfTheLine();*/ new TreasurePick();
		
			List<Player> g = new ArrayList<>();
			g.add(mario);
//			g.add(luigi);
//			g.add(peach);
//			g.add(yoshi);
			
			m.playGame(g, this);
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

		startsOnLeft.setSpritePos((int) (stage.getWidth() + 40), 342);
		startsOnRight.setSpritePos(-40, 385);

		startsOnLeft.walk(785, 342);
		Platform.runLater(() ->{ startsOnLeft.setSprite(SpriteState.STILL_LEFT); });

		startsOnRight.walk(141, 385);
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
			// set everyone to starting spaces
			AbstractSpace start  = board.getSpaces().get(41);
			int x = start.getXPos();
			int y = start.getYPos();

			//			setDialogText("Roll for each character, to\ndetermine the turn order!", false, -1);

			luigi.setCurrSpace(start);
			luigi.setSpritePos(x, y);

			mario.setCurrSpace(start);
			mario.setSpritePos(x, y);

			peach.setCurrSpace(start);
			peach.setSpritePos(x, y);

			yoshi.setCurrSpace(start);
			yoshi.setSpritePos(x, y);
			yoshi.addCoins(21);


			// sort them by their dice rolls
			List<SortablePlayer> sortablePlayer = new ArrayList<>();
			sortablePlayer.add(new SortablePlayer(luigi.getNextRoll(), luigi));
			System.out.println("luigi rolled");

			sortablePlayer.add(new SortablePlayer(mario.getNextRoll(), mario));
			System.out.println("mario rolled");

			sortablePlayer.add(new SortablePlayer(peach.getNextRoll(), peach));
			System.out.println("peach rolled");

			sortablePlayer.add(new SortablePlayer(yoshi.getNextRoll(), yoshi));
			System.out.println("yoshi rolled");

			Collections.sort(sortablePlayer);
			Collections.reverse(sortablePlayer);

			List<Player> players = new ArrayList<>(4);
			players.add(sortablePlayer.get(0).player);
			players.add(sortablePlayer.get(1).player);
			players.add(sortablePlayer.get(2).player);
			players.add(sortablePlayer.get(3).player);

			// report on the order of the players
			String playerList ="Players will go in this order:\n";
			for(Player p : players)
				playerList += p.getName() +" ";

			setDialogText(playerList, false, 2000);

			// play the game, infinitely for now
			while(true) {
				for(Player p : players) {
					System.out.println("taking turn for player " +p.getName());
					takeTurn(p);
				}
			}
		});

		t.setDaemon(true);
		t.setName("Game Thread");
		t.start();
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
		FadeTransition t = new FadeTransition(Duration.millis(time), fadeOutImg);
		t.setToValue(opacity);
		t.play();
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

}





















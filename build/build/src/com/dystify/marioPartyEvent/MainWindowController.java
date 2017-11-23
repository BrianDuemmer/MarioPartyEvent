package com.dystify.marioPartyEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.dystify.marioPartyEvent.control2.CommandIns;
import com.dystify.marioPartyEvent.util.Util;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class MainWindowController 
{
	private DisplayController dc;
	@FXML ToggleButton fadeBtn;
	@FXML TextField rollNumField;
	@FXML ChoiceBox<String> rollTeamSelector;
	@FXML CheckBox endGameCheckbox;

	@FXML Label next4pMinigameLbl;
	@FXML Label nextspMinigameLbl;
	@FXML Label nextBowserMinigameLbl;




	//    @FXML void add50Coins(ActionEvent event) { dc.addCoins(50); }
	//    @FXML void add5Coins(ActionEvent event) { dc.addCoins(5); }
	//    @FXML void remove50Coins(ActionEvent event) { dc.addCoins(-50); }
	//    @FXML void remove5Coins(ActionEvent event) { dc.addCoins(-5); }
	//    
	//    @FXML void moveDiagUR(ActionEvent event) {dc.testMove(100, -100);}
	//    @FXML void moveDown(ActionEvent event) {dc.testMove(0, 100);}
	//    @FXML void moveLeft(ActionEvent event) {dc.testMove(-100, 0);}
	//    @FXML void moveRight(ActionEvent event) {dc.testMove(100, 0);}
	//    @FXML void moveUp(ActionEvent event) {dc.testMove(0, -100);}
	//
	//
	//    @FXML void setPlaceFirst(ActionEvent event) { dc.testScoreBoard.setPlace(firstPlaceImg); }
	//    @FXML void setPlaceFourth(ActionEvent event) { dc.testScoreBoard.setPlace(fourthPlaceImg); }
	//    @FXML void setPlaceSecond(ActionEvent event) { dc.testScoreBoard.setPlace(secondPlaceImg); }
	//    @FXML void setPlaceThird(ActionEvent event) { dc.testScoreBoard.setPlace(thirdPlaceImg); }




	@FXML void openDisplay(ActionEvent event) {
		System.out.println("open");
		dc = new DisplayController();
		dc.show();
	}


	@FXML void closeDisplay(ActionEvent event) {
		System.out.println("close");
		dc.hide();
	}


	@FXML void onFadeBtn(ActionEvent event) {
		boolean hit = fadeBtn.isSelected();
		//		new Thread(()->{
		//			dc.swapBowserToad();
		//			dc.getBoard().invertStarSpaces();
		//		}).start();

		if(hit) {
			fadeBtn.setText("Fade out...");
			dc.fadeTo(0, 2000);
		} else {
			fadeBtn.setText("Fade in");
			dc.fadeTo(1, 2000);
		}
	}



	@FXML void testMinigame(ActionEvent event) {
		dc.testMinigame();
	}




	/**
	 * <ul>
	 * 	<li>right: 2</li>
	 * 	<li>up: 8</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectDirectionUp(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!right", "USER, you chose right!"), 2);
		lCmds.put(new CommandIns("!up", "USER, you chose right!"), 8);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}





	/**
	 * <ul>
	 * 	<li>right: 8</li>
	 * 	<li>up: 2</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectDirectionRight(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!right", "USER, you chose right!"), 8);
		lCmds.put(new CommandIns("!up", "USER, you chose up!"), 2);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}







	/**
	 * <ul>
	 * 	<li>right: 2</li>
	 * 	<li>down: 8</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectDirectionDown(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!right", "USER, you chose right!"), 2);
		lCmds.put(new CommandIns("!down", "USER, you chose down!"), 8);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}





	/**
	 * <ul>
	 * 	<li>left: 8</li>
	 * 	<li>up: 2</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectDirectionLeft(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!left", "USER, you chose left!"), 8);
		lCmds.put(new CommandIns("!up", "USER, you chose up!"), 2);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}





	/**
	 * <ul>
	 * 	<li>middle: 8</li>
	 * 	<li>up: 2</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectDirectionMiddle(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!middle", "USER, you chose middle!"), 8);
		lCmds.put(new CommandIns("!up", "USER, you chose up!"), 2);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}





	/**
	 * <ul>
	 * 	<li>red: 8</li>
	 * 	<li>blue: 2</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectColorRed(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!red", "USER, you chose red!"), 8);
		lCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 2);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}






	/**
	 * <ul>
	 * 	<li>green: 8</li>
	 * 	<li>blue: 2</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectColorGreen(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!green", "USER, you chose green!"), 8);
		lCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 2);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}






	/**
	 * <ul>
	 * 	<li>yellow: 8</li>
	 * 	<li>blue: 2</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectColorYellow(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!yellow", "USER, you chose yellow!"), 8);
		lCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 2);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}





	/**
	 * <ul>
	 * 	<li>Blue: 8</li>
	 * 	<li>Red: 2</li>
	 * </ul>
	 * @param event
	 */
	@FXML void injectColorBlue(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!red", "USER, you chose red!"), 2);
		lCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 8);

		cmds.put(rollTeamSelector.getSelectionModel().getSelectedItem(), lCmds);
		Util.smartIftttInject(cmds, 5000);
	}



	/**
	 * Merry Go Chomp preset
	 *<table border="2">
	 *	<tr>
	 *		<th></th>
	 *		<th>Luigi</th>
	 *		<th>Mario</th>
	 *		<th>Peach</th>
	 *		<th>Yoshi</th>
	 *		<th>Winner:</th>
	 *	</tr>
	 *	<tr>
	 *		<th>Red</th>
	 *		<th>10</th>
	 *		<th>1</th>
	 *		<th>1</th>
	 *		<th>4</th>
	 *		<th>Luigi</th>
	 *	</tr>
	 *	<tr>
	 *		<th>Blue</th>
	 *		<th>5</th>
	 *		<th>2</th>
	 *		<th>6</th>
	 *		<th>3</th>
	 *		<th>Peach</th>
	 *	</tr>
	 *	<tr>
	 *		<th>Yellow</th>
	 *		<th>0</th>
	 *		<th>5</th>
	 *		<th>1</th>
	 *		<th>1</th>
	 *		<th>Mario</th>
	 *	</tr>
	 *	<tr>
	 *		<th>Green</th>
	 *		<th>0</th>
	 *		<th>4</th>
	 *		<th>2</th>
	 *		<th>5</th>
	 *		<th>Yoshi</th>
	 *	</tr>
	 *</table>
	 * @param event
	 */
	@FXML void injectColorPreset1(ActionEvent event) {
		Map<String, Map<CommandIns, Integer>> cmds = new HashMap<>();

		Map<CommandIns, Integer> lCmds = new HashMap<>();
		lCmds.put(new CommandIns("!red", "USER, you chose red!"), 10);
		lCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 5);
		lCmds.put(new CommandIns("!yellow", "USER, you chose red!"), 0);
		lCmds.put(new CommandIns("!green", "USER, you chose blue!"), 0);
		cmds.put("l", lCmds);


		Map<CommandIns, Integer> mCmds = new HashMap<>();
		mCmds.put(new CommandIns("!red", "USER, you chose red!"), 1);
		mCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 2);
		mCmds.put(new CommandIns("!yellow", "USER, you chose red!"), 5);
		mCmds.put(new CommandIns("!green", "USER, you chose blue!"), 4);
		cmds.put("m", mCmds);

		Map<CommandIns, Integer> pCmds = new HashMap<>();
		pCmds.put(new CommandIns("!red", "USER, you chose red!"), 1);
		pCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 6);
		pCmds.put(new CommandIns("!yellow", "USER, you chose red!"), 1);
		pCmds.put(new CommandIns("!green", "USER, you chose blue!"), 2);
		cmds.put("p", pCmds);

		Map<CommandIns, Integer> yCmds = new HashMap<>();
		yCmds.put(new CommandIns("!red", "USER, you chose red!"), 4);
		yCmds.put(new CommandIns("!blue", "USER, you chose blue!"), 3);
		yCmds.put(new CommandIns("!yellow", "USER, you chose red!"), 1);
		yCmds.put(new CommandIns("!green", "USER, you chose blue!"), 4);
		cmds.put("l", yCmds);

		Util.smartIftttInject(cmds, 5000);
	}








	@FXML void injectRoll(ActionEvent event) {
		int rollVal = 0;

		try { rollVal = Integer.parseInt(this.rollNumField.getText().trim()); } 
		catch (Exception e) { e.printStackTrace(); }

		String userID = rollTeamSelector.getSelectionModel().getSelectedItem().toUpperCase().trim() + "_DUMMYMEMBER";

		Util.iftttInject(5000, 
				new CommandIns("!roll", "EventApp, you rolled a " +rollVal+ "!(" +userID+ ")", userID));
	}




	@FXML void injectWinner(ActionEvent event) {
		String winCmd = "!winner " + String.valueOf(rollTeamSelector.getSelectionModel().getSelectedItem().toLowerCase().charAt(0));
		String userID = "EventApp";
		Util.iftttInject(5000, 
				new CommandIns(winCmd, "Looks like " +rollTeamSelector.getSelectionModel().getSelectedItem()+ "!(" +userID+ ")", userID));
	}



	@FXML void onEndGameSet(ActionEvent event) {
		dc.setEndNextTurn(endGameCheckbox.isSelected());
	}







	/**
	 * FXML methods Over
	 */
	@FXML void initialize() {
		rollNumField.textProperty().addListener(
				(ObservableValue<? extends String> observable, String oldValue, String newValue)-> {
					if (!newValue.matches("\\d*")) {
						rollNumField.setText(newValue.replaceAll("[^\\d]", ""));
					}
					if(newValue.trim().equals(""))
						rollNumField.setText("0");
				});
		rollNumField.setText("0");

		rollTeamSelector.setItems(FXCollections.observableArrayList("Luigi", "Mario", "Peach", "Yoshi"));
		rollTeamSelector.getSelectionModel().select(0);

		ScheduledExecutorService sex = Executors.newSingleThreadScheduledExecutor(
				new ThreadFactory() {
					@Override public Thread newThread(Runnable r) {
						Thread t = new Thread(r);
						t.setDaemon(true);
						return t;
					}
				});
		sex.scheduleAtFixedRate(() -> 
			{
				if(dc != null) {
					Platform.runLater(() -> {
						String p4txt = "Next 4 player Minigame: " +dc.getNext4pMinigame();
						String sptxt = "Next singleplayer Minigame: " +dc.getNextspMinigame();
						String btxt = "Next Bowser Minigame: " +dc.getNext4pMinigame();
	
						next4pMinigameLbl.setText(p4txt);
						nextspMinigameLbl.setText(sptxt);
						nextBowserMinigameLbl.setText(btxt);
					});
				}
			}, 
			0, 
			500, 
			TimeUnit.MILLISECONDS
		);
	}






}

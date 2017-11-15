package com.dystify.marioPartyEvent;

import com.dystify.marioPartyEvent.control.Comm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class MainWindowController 
{
	private DisplayController dc;
	@FXML ToggleButton fadeBtn;
	
	
	
	
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
		
		if(!hit) {
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
     * FXML methods Over
     */
	@FXML void initialize() {
	}
	
	
	
	


}

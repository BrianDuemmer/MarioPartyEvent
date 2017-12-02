package com.dystify.marioPartyEvent.graphic;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;

/**
 * represents a character's score display
 * @author Duemmer
 *
 */
public class ScoreBoard extends StackPane
{
	// using the labels themselves doesn't work, so keep dedicated locks
	private Object numCoinsLock = new Object();
	private Object numStarsLock = new Object();


	// coordinates for root offset, stars text, coins text, and place overlay
	private static final int STARS_X = 25;
	private static final int STARS_Y = 29;

	private static final int COINS_X = STARS_X;
	private static final int COINS_Y = -15;

	private static final int PLACE_X = 0;
	private static final int PLACE_Y = 0;

	private static final int FACE_X = 0;
	private static final int FACE_Y = 0;

	private static final int SCROLL_MILLIS = 1000;

	private int numStars;
	private int numCoins;

	private Label starsLbl;
	private Label coinsLbl;

	private ImageView placeDisp;
	private ImageView playerFace;
	private ImageView hudBase;




	public ScoreBoard(Image hudImg, Paint color) {
		super();

		// initialize all the components
		starsLbl = new Label();
		coinsLbl = new Label();
		placeDisp = new ImageView();
		playerFace = new ImageView();
		hudBase = new ImageView(hudImg);

		// apply all of the coordinate transformations
		starsLbl.setTranslateX(STARS_X);
		starsLbl.setTranslateY(STARS_Y);

		coinsLbl.setTranslateX(COINS_X);
		coinsLbl.setTranslateY(COINS_Y);

		placeDisp.setTranslateX(PLACE_X);
		placeDisp.setTranslateY(PLACE_Y);

		playerFace.setTranslateX(FACE_X);
		playerFace.setTranslateY(FACE_Y);
		
		// set alignment of textboxes
		starsLbl.setAlignment(Pos.TOP_LEFT);
		coinsLbl.setAlignment(Pos.TOP_LEFT);

		//add css to the whole thing
		starsLbl.setTextFill(color);
		coinsLbl.setTextFill(color);

		// init counters
		setCoinsCore(0);
		setStarsCore(0);

		// add to the pane
		getChildren().addAll(hudBase, starsLbl, coinsLbl, placeDisp, playerFace);

	}




	/**
	 * Sets the image to use as the character's display face on the scoreboard. Updates immediately, and auto resizes
	 */
	public void setFace(Image face) {
		playerFace.setImage(face);
	}

	/**
	 * Sets the image to use as the character's display rank on the scoreboard. Updates immediately, and auto resizes
	 */
	public void setPlace(Image place) {
		Platform.runLater(()->{placeDisp.setImage(place);});
	}







	/**
	 * sets the coin counter to this value. It will scroll there, not just set it, in some fixed amount of time, or instantly
	 * if force is set to true
	 * @param coins
	 */
	public void setCoins(int coins, boolean force) {
		int delta = coins - numCoins;
		numCoins = coins;

		if(delta != 0) {
			long delay = SCROLL_MILLIS / Math.abs(delta);

			if(!force)  {
				// Create a daemon, loop through and periodically increment/decrement the coin counter
				Thread t = new Thread(()-> {
					synchronized(numCoinsLock) { // it's possible two threads could try to do this at once
						int i = delta;
						if(delta > 0)
							while(i >= 0)  {
								setCoinsCore(coins - i);
								try { Thread.sleep(delay); } catch(Exception e) {};
								i--;
							}
						else
							while(i <= 0)  {
								setCoinsCore(coins - i);
								try { Thread.sleep(delay); } catch(Exception e) {};
								i++;
							}
					}
				});

				t.setDaemon(true);
				t.start();
			}
		}
	}

	/**
	 * "lOcAl VaRiAbLe I dEfInEd In An EnClOsInG sCoPe MuSt Be FiNaL oR eFfEcTiVeLy FiNaL" - The Damned Compiler
	 */
	private void setCoinsCore(int coins) {
		Platform.runLater(() -> { 
			coinsLbl.setText("x" +coins); // may add static text too
		});
	}









	/**
	 * sets the coin counter to this value. It will scroll there, not just set it, in some fixed amount of time, or instantly
	 * if force is set to true
	 * @param stars
	 */
	public void setStars(int stars, boolean force) {
		int delta = stars - numStars;
		numStars = stars;

		if(delta != 0) {
			long delay = SCROLL_MILLIS / Math.abs(delta);

			if(!force)  {
				// Create a daemon, loop through and periodically increment/decrement the coin counter
				Thread t = new Thread(()-> {
					synchronized(numStarsLock) { // it's possible two threads could try to do this at once
						int i = delta;
						if(delta > 0)
							while(i >= 0)  {
								setStarsCore(stars - i);
								try { Thread.sleep(delay); } catch(Exception e) {};
								i--;
							}
						else
							while(i <= 0)  {
								setStarsCore(stars - i);
								try { Thread.sleep(delay); } catch(Exception e) {};
								i++;
							}
					}
				});

				t.setDaemon(true);
				t.start();
			}
		}
	}

	private void setStarsCore(int stars) {
		Platform.runLater(() -> { 
			starsLbl.setText("x" +stars);  // may add static text too
			System.out.println("Set stars " +stars);
		});
	}




	public int getNumStars() {
		return numStars;
	}




	public int getNumCoins() {
		return numCoins;
	}

}





















package com.dystify.marioPartyEvent.graphic;

import java.util.Arrays;
import java.util.List;

import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.command.RollCommand;
import com.dystify.marioPartyEvent.space.AbstractSpace;
import com.dystify.marioPartyEvent.util.Util;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * represents a player on the mario party board. This will be either be
 * mario, luigi, peach, or yoshi
 * @author Duemmer
 *
 */
public class Player
{
	private static final double SPRITE_SCL = 2;
	
	// allows us to block until walk() finishes
	private Object walkLock = new Object();
	
	private String name;
	private String resourceDir;
	
	private ScoreBoard sb;
	
	private int numStars;
	private int numCoins;
	
	private int currX;
	private int currY;
	
	private int pxPerSecond = 223;
	private double spriteXOff = 0;
	private double spriteYOff = 0;
	
	private Place currRank;
	
	private ImageView playerSprite;
	
	private AbstractSpace currSpace;
	
	
	public Player(String name, String resourceDir, Paint p) {
		this.setName(name);
		this.resourceDir = resourceDir;
		playerSprite = new ImageView();
		setSprite(SpriteState.STILL);
		
		sb = new ScoreBoard(Util.loadImage(resourceDir + "/hud_base.png"), p);
		setActiveFace(false);
		currRank = Place.FIRST;
		setPlace(currRank);
	}
	
	
	/**
	 * creates a shadow player w/o any image assets
	 * @param resourceDir
	 */
	public Player(String name) {
		this.name = name;
	}
	
	
	/**
	 * sets up barebones image assets, only sprite, for the player object
	 * @param resourceDir
	 */
	public void initShadow(String resourceDir) {
		this.resourceDir = resourceDir;
		playerSprite = new ImageView();
	}
	
	
	
	
	
	
	
	
	/**
	 * Adds the specified number of coins on the player. If the number is negative, and 
	 * would cause the player to have less than 0 coins, their final coin count will be coerced to 0
	 * @param coins
	 */
	public void addCoins(int coins) {
		numCoins += coins;
		numCoins = numCoins < 0 ? 0 : numCoins;
		sb.setCoins(numCoins, false);
	}
	
	
	public void addStars(int stars) {
		numStars += stars;
		numStars = numStars < 0 ? 0 : numStars;
		sb.setStars(numStars, false);
		System.out.println("stars: " +numStars);
	}
	
	
	
	
	
	
	
	/**
	 * Has the player sprite move to the specified coordinates. It will select the correct walk animation based
	 * on the angle of the move, which is linear interpolation. NOTE: does not reset sprite to idle, and should not 
	 * be called from jfx thread
	 * @param toX
	 * @param toY
	 */
	public synchronized void walk(int toX, int toY)
	{
		System.out.println("Player.walk() PLAYER:" +name);
		double dx = (toX - currX);
		double dy = (toY - currY);
		double dist = Math.sqrt(dx*dx + dy*dy);
		
		// image coordinates have an inverted y axis, so swap when getting angle
		double ang = Math.atan2(dy, -dx);
//		System.out.println(Math.toDegrees(ang));
		
		// only move if we will have net movement
		if(dist > 0) 
		{	
			Platform.runLater(() -> {
				System.out.println("Player.walk(), runLater lambda PLAYER: " +name);
				// not all the sprites have diagonal animations, If this throws an exception then we are probably 
				// trying to run a diagonal animation for a character that doesn't have one, so just do it with cardinal directions
				try {
					setSprite(SpriteState.fromAngle(ang));
					
					double dur = dist / pxPerSecond;
					TranslateTransition t = new TranslateTransition();
					t.setToX(toX-spriteXOff);
					t.setToY(toY-spriteYOff);
					t.setDuration(Duration.seconds(dur));
					t.setInterpolator(Interpolator.LINEAR);
					t.setNode(playerSprite);
					
					t.setOnFinished((ActionEvent e) -> {
//						setSpritePos(toX+100, toY+100);
						synchronized(walkLock) { walkLock.notifyAll(); }
					});
					
					t.play();
					
					
				} catch(Exception e) {
					System.out.println("in walk(), encountered " +e.getClass().getName()+ "for Player:" +name);
					
					double timeX = Math.abs(dx) / pxPerSecond;
					double timeY = Math.abs(dy) / pxPerSecond;
					
					SpriteState vertSprite = dy < 0 ? SpriteState.UP : SpriteState.DOWN; // need to invert b/c image y-axis inverted
					SpriteState horzSprite = dx > 0 ? SpriteState.RIGHT : SpriteState.LEFT;
					
					TranslateTransition vert = new TranslateTransition();
					vert.setToY(toY-spriteYOff);
					vert.setDuration(Duration.seconds(timeY));
					vert.setNode(setSprite(vertSprite));
					vert.setInterpolator(Interpolator.LINEAR);
					
					vert.setOnFinished((ActionEvent foo) -> { // play the horizontal move right after
						TranslateTransition horz = new TranslateTransition();
						horz.setToX(toX-spriteXOff);
						horz.setDuration(Duration.seconds(timeX));
						horz.setNode(setSprite(horzSprite));
						horz.setInterpolator(Interpolator.LINEAR);
						horz.setOnFinished((ActionEvent bar) -> {
//							setSpritePos(toX+100, toY+100);
							synchronized(walkLock) { walkLock.notifyAll(); }
						});
						
						horz.play();
					});
					
					vert.play();
				}
				
			});
			synchronized(walkLock ) {
				try { walkLock.wait(150000); } // 150 seconds should be long enough for pretty much any walk cycle
				catch (InterruptedException e) { e.printStackTrace(); }
			}
			
			currX = toX;
			currY = toY;
		}
	}
	
	
	
	
	
	
	public void setScoreboardPos(int x, int y) {
		sb.setTranslateX(x);
		sb.setTranslateY(y);
	}
	
	
	
	
	/**
	 * fetches the next registered !roll command from this player's registered Mod Master. Blocks until that command is recieved
	 * @return the value of the roll
	 */
	public int getNextRoll() {
		RollCommand c = CommandReader.inst().pollForCommand(Arrays.asList(this), new RollCommand(0), -1);
		return c.getRollNum();
	}
	
	
	
	
	
	public static Player getByTeamString(String team, List<Player> players) {
		for(Player p : players)
			if(team.equalsIgnoreCase(p.getFirstLetter()))
				return p;
		System.err.println("Could not find player from given team string \"" +team+ "\"");
		return null;
	}
	
	
	
	
	
	/**
	 * 
	 * @param active true->this player's turn face; false->default face
	 */
	public void setActiveFace(boolean active) {
		if(active)
			sb.setFace(Util.loadImage(resourceDir +"/hud_turn.png"));
		else
			sb.setFace(Util.loadImage(resourceDir +"/hud_idle.png"));
	}
	
	
	
	public void setPlace(Place p) {
		sb.setPlace(Util.loadImage(p.getPlace()));
		currRank = p;
	}
	
	
	
	
	
	/**
	 * does not need to be in jfx thread
	 * @param x
	 * @param y
	 */
	public void setSpritePos(int x, int y) {
		Platform.runLater(() -> {
			playerSprite.setTranslateX(x - spriteXOff);
			playerSprite.setTranslateY(y - spriteYOff);
		});
		
		currX = x;
		currY = y;
	}
	
	
	/**
	 * should run in javafx thread
	 * @param sprite
	 * @return
	 */
	public ImageView setSprite(SpriteState sprite) {
		Image i = Util.loadImage(resourceDir +"/"+ sprite);
		playerSprite.setImage(i);
		playerSprite.setScaleX(SPRITE_SCL);
		playerSprite.setScaleY(SPRITE_SCL);
		
		spriteYOff = i.getHeight()-5;
		spriteXOff = i.getWidth() / 2;
		
		return playerSprite;
	}
	
	
	
	
	
	
	
	
	/**
	 * gets a string containing the first letter of each player's name, lowercase, for database searches
	 * @param players
	 * @return
	 */
	public static String getTeamsSearchString(List<Player> players) {
		StringBuilder b = new StringBuilder();
		for(Player p : players) 
			b.append(p.getName().toLowerCase().charAt(0));
		return b.toString();
	}
	
	
	
	
	
	public String getFirstLetter() { return String.valueOf(name.charAt(0)); }





	public ImageView getSprite() {
		return playerSprite;
	}





	public ScoreBoard getSb() {
		return sb;
	}





	public AbstractSpace getCurrSpace() {
		return currSpace;
	}





	public void setCurrSpace(AbstractSpace currSpace) {
		this.currSpace = currSpace;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public int getNumStars() {
		return numStars;
	}





	public int getNumCoins() {
		return numCoins;
	}


	public int getCurrX() {
		return currX;
	}


	public int getCurrY() {
		return currY;
	}


	/**
	 * returns equal if obj is a Player with the same name as this player, false otherwise
	 */
	@Override public boolean equals(Object obj) 
	{
		boolean eq = false;
		try {
			eq = ((Player)obj).getName().trim().toLowerCase().charAt(0) == name.toLowerCase().charAt(0);
		}catch (Exception e) {} // can't cast to a player, so definitely not equal
		return eq;
	}


	public Place getCurrRank() {
		return currRank;
	}
	
	
	
}














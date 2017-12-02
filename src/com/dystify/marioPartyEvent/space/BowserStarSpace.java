package com.dystify.marioPartyEvent.space;

import java.util.Arrays;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.graphic.Player;

public class BowserStarSpace extends AbstractSpace 
{
	private boolean isStar;

	public BowserStarSpace(int spaceNum, int xPos, int yPos, int nextSpace, int branchNext) {
		super(spaceNum, xPos, yPos, nextSpace, branchNext);
	}
	
	public BowserStarSpace(int spaceNum, int xPos, int yPos, int nextSpace, int branchNext, boolean defaultStar) {
		super(spaceNum, xPos, yPos, nextSpace, branchNext);
		this.isStar = defaultStar;
	}

	@Override
	public Player onPassed(Player c, DisplayController disp) {
		return c;
	}

	@Override
	public Player onLandedOn(Player c, DisplayController disp) 
	{
		if(isStar) {
			disp.setDialogText("Would you like to buy a star for 20 coins, " +c.getName()+ "?", false, -1);
			try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
			
			if(c.getNumCoins() >= 20) {
				disp.setDialogText(String.format("%s's team: Vote !yes or !no", c.getName()), true, -1);
				String resp = CommandReader.inst().voteOneTeam(c, Filter.FILTER_YESNO, Main.chatVoteMillis).replaceAll("!", "");
				if(resp.trim().equalsIgnoreCase("yes")) {
					c.addCoins(-20);
					c.addStars(1);
				}
			} else {
				disp.setDialogText("Oh... looks like you can't afford to buy a star. !OOF", false, Main.dialogWaitMillis);
				try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
			}
		} else { // play a bowser game, all characters
			disp.getNextBowserMinigame(true).playGame(Arrays.asList(disp.getMario(), disp.getLuigi(), disp.getPeach(), disp.getYoshi()), disp);
		}
		return c;
	}

	public boolean isStar() {
		return isStar;
	}
	
	
	public boolean toggleStar() {
		isStar = !isStar;
		return isStar;
	}

	public void setStar(boolean isStar) {
		this.isStar = isStar;
	}

}

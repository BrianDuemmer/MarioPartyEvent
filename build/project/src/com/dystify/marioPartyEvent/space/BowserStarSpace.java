package com.dystify.marioPartyEvent.space;

import com.dystify.marioPartyEvent.DisplayController;
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
			disp.setDialogText("Would you like to buy a star for\n20 coins, " +c.getName()+ "?", false, -1);
			try { Thread.sleep(1500); }catch(Exception e) {}
			
			if(c.getNumCoins() >= 20) {
//				disp.setDialogText("!choose yes or no", true, 3000);
//				String resp = Comm.getInst().getChoice(20000, ChooseType.YESNO, c.getFirstLetter());
//				if(resp.trim().equalsIgnoreCase("yes")) {
//					c.addCoins(-20);
//					c.addStars(1);
//				}
			} else {
				disp.setDialogText("Oh... looks like you can't\nafford to buy a star.", true, 3000);
				try { Thread.sleep(1500); }catch(Exception e) {}
			}
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

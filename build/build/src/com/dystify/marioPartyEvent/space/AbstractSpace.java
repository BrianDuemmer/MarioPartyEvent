package com.dystify.marioPartyEvent.space;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

/**
 * Represents a space on the Mario party board.
 * @author Duemmer
 *
 */
public abstract class AbstractSpace 
{
	protected int xPos;
	protected int yPos;
	
	protected int nextSpace;
	protected int branchNext;
	
	protected int spaceNum;
	
	
	public abstract Player onPassed(Player c, DisplayController disp);
	public abstract Player onLandedOn(Player c, DisplayController disp);
	
	
	
	
	
	
	/**
	 * 
	 * @param spaceNum the index number of this space
	 * @param xPos x coordinate of the space
	 * @param yPos y coordinate of the space
	 * @param nextSpace reference to the next space after this one
	 * @param branchNext an optional branch space. set to a negative value to disable
	 */
	public AbstractSpace(int spaceNum, int xPos, int yPos, int nextSpace, int branchNext) 
	{
		this.spaceNum = spaceNum;
		this.xPos = xPos;
		this.yPos = yPos;
		this.nextSpace = nextSpace;
		this.branchNext = branchNext;
	}
	
	
	
	
	
	public boolean isBranched() {
		return branchNext >= 0;
	}
	
	
	
	@Override public String toString() {
		String fmt = "%s: x:%d y:%d next:%d branch:%d";
		return String.format(fmt, this.getClass().getName(), xPos, yPos, nextSpace, branchNext);
		
	}
	public int getXPos() {
		return xPos;
	}
	public int getYPos() {
		return yPos;
	}
	public int getNextSpace() {
		return nextSpace;
	}
	public int getBranchNext() {
		return branchNext;
	}
	public int getSpaceNum() {
		return spaceNum;
	}
	
	
	
	
	public void onLandedOnCore(Player p, DisplayController disp) 
	{
		onLandedOn(p, disp);
	}
	public void onPassedCore(Player p, DisplayController disp) 
	{
		onPassed(p, disp);
	}
	
	
	
	
	
	
	public String branchDir(AbstractSpace other) {
		int dx = other.xPos - this.xPos;
		int dy = other.yPos - this.yPos;
		
		// convert to a range of -1-7
		double angRaw = Math.atan2(dy, -dx);
		angRaw /= Math.PI;
		angRaw *= 4;
		angRaw += 3;
		
		String type = "";
		
		if(angRaw <= 1) { type = "!right"; }
		else if(angRaw <= 3) { type = "!up"; }
		else if(angRaw <= 5) { type = "!left"; }
		else if(angRaw <= 7) { type = "!down"; }
		
		return type;
	}
	
}

















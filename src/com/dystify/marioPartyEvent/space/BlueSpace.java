package com.dystify.marioPartyEvent.space;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

/**
 * Just a plain ol' blue space. gives 3 coins if you land on it
 * @author Duemmer
 *
 */
public class BlueSpace extends AbstractSpace {

	public BlueSpace(int spaceNum, int xPos, int yPos, int nextSpace, int branchNext) {
		super(spaceNum, xPos, yPos, nextSpace, branchNext);
	}

	@Override
	public Player onPassed(Player c, DisplayController disp) { System.out.println("passed blue space"); return c; }

	@Override
	public Player onLandedOn(Player c, DisplayController disp) {
		System.out.println("landed on blue space");
		c.addCoins(3);
		return c;
	}

}

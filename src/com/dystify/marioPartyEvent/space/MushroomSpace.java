package com.dystify.marioPartyEvent.space;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.graphic.Player;

public class MushroomSpace extends AbstractSpace {

	public MushroomSpace(int spaceNum, int xPos, int yPos, int nextSpace, int branchNext) {
		super(spaceNum, xPos, yPos, nextSpace, branchNext);
	}

	@Override
	public Player onPassed(Player c, DisplayController disp) {
		return c;
	}

	@Override
	public Player onLandedOn(Player c, DisplayController disp) { // add an extra turn
		System.out.println("MushroomSpace.onLandedOn()");
		disp.setDialogText("Roll again, " +c.getName()+ "!", false, Main.dialogWaitMillis);
		disp.takeTurn(c);
		return c;
	}

}

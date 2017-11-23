package com.dystify.marioPartyEvent.space;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

public class SoloMinigameSpace extends AbstractSpace {

	public SoloMinigameSpace(int spaceNum, int xPos, int yPos, int nextSpace, int branchNext) {
		super(spaceNum, xPos, yPos, nextSpace, branchNext);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Player onPassed(Player c, DisplayController disp) {
		// TODO Auto-generated method stub
		return c;
	}

	@Override
	public Player onLandedOn(Player c, DisplayController disp) {
		// TODO Auto-generated method stub
		return c;
	}

}

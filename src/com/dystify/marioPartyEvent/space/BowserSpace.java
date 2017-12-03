package com.dystify.marioPartyEvent.space;

import java.util.Arrays;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

public class BowserSpace extends AbstractSpace {

	public BowserSpace(int spaceNum, int xPos, int yPos, int nextSpace, int branchNext) {
		super(spaceNum, xPos, yPos, nextSpace, branchNext);
	}

	@Override
	public Player onPassed(Player c, DisplayController disp) {
		return c;
	}

	@Override
	public Player onLandedOn(Player c, DisplayController disp) {
		disp.getNextBowserMinigame(true).playGame(Arrays.asList(disp.getMario(), disp.getLuigi(), disp.getPeach(), disp.getYoshi()), disp);
		return c;
	}

}

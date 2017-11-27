package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;

public class SpotTheDifference extends AbstractChatOnlyMinigame {

	public SpotTheDifference() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Find the differences in the\npictures, each difference\nis one point", false, 2500);
		try { Thread.sleep(2500); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Spot the Difference"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

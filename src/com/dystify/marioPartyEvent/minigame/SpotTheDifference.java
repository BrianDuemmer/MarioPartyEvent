package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;

public class SpotTheDifference extends AbstractChatOnlyMinigame {

	public SpotTheDifference() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Find the differences in the pictures, each difference is one point", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Spot the Difference"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

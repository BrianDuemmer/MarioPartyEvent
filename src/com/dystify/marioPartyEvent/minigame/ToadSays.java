package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;

public class ToadSays extends AbstractChatOnlyMinigame {

	public ToadSays() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Text demo for Toad Says", false, 2500);
		try { Thread.sleep(2500); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Toad Says"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

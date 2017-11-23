package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;

public class GuessTheQuote extends AbstractChatOnlyMinigame {

	public GuessTheQuote() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Text demo for Guess the Quote", false, 2500);
		try { Thread.sleep(2500); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Guess the Quote"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

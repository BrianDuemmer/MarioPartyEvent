package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;

public class GuessTheQuote extends AbstractChatOnlyMinigame {

	public GuessTheQuote() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Determine the character that matches with the quote that appears", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Guess the Quote"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

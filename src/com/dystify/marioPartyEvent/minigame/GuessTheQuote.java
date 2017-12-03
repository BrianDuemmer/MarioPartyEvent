package com.dystify.marioPartyEvent.minigame;

import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.graphic.Player;

public class GuessTheQuote extends AbstractChatOnlyMinigame {

	public GuessTheQuote() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp, List<Player> players) {
		disp.setDialogText("Determine the character that matches...", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("...with the quote that appears", false, -1); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Guess the Quote"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

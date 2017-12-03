package com.dystify.marioPartyEvent.minigame;

import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.graphic.Player;

public class ToadSays extends AbstractChatOnlyMinigame {

	public ToadSays() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp, List<Player> players) {
		disp.setDialogText("Pay attention to Toad, only do what he asks when he says “Toad Says”", false, -1);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Toad Says"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

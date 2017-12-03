package com.dystify.marioPartyEvent.minigame;

import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.graphic.Player;

public class SpotTheDifference extends AbstractChatOnlyMinigame {

	public SpotTheDifference() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp, List<Player> players) {
		disp.setDialogText("Find the differences in the pictures, each difference is one point", false, -1);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Spot the Difference"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

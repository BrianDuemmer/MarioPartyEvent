package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;

public class RoleCall extends AbstractChatOnlyMinigame {

	public RoleCall() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Count each object on the screen,\ntag the game host with\nthe right answer", false, 2500);
		try { Thread.sleep(2500); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Role Call"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

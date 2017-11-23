package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;

public class RoleCall extends AbstractChatOnlyMinigame {

	public RoleCall() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Text demo for Role Call", false, 2500);
		try { Thread.sleep(2500); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Role Call"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

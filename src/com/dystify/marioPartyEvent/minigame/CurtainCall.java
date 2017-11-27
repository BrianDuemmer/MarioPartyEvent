package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;

public class CurtainCall extends AbstractChatOnlyMinigame {

	public CurtainCall() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Pay attention to the characters\nin line, and answer the\nhosts' questions correctly!", false, 2500);
		try { Thread.sleep(2500); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Curtain Call"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

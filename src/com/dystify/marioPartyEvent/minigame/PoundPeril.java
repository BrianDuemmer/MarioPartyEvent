package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;

public class PoundPeril extends AbstractChatOnlyMinigame {

	public PoundPeril() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Text demo for Pound Peril", false, 2500);
		try { Thread.sleep(2500); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Pound Peril"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

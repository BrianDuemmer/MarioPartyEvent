package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;

public class CurtainCall extends AbstractChatOnlyMinigame {

	public CurtainCall() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Pay attention to the characters in line!", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("Answer the hosts' questions correctly!", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Curtain Call"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

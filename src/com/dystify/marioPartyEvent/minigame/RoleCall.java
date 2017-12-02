package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;

public class RoleCall extends AbstractChatOnlyMinigame {

	public RoleCall() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Count each object on the screen...", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("Tag the game host with the right answer", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Role Call"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

package com.dystify.marioPartyEvent.minigame;

import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.graphic.Player;

public class RoleCall extends AbstractChatOnlyMinigame {

	public RoleCall() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp, List<Player> players) {
		disp.setDialogText("Count each object on the screen...", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("Tag the game host with the right answer", false, -1);
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Role Call"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

package com.dystify.marioPartyEvent.minigame;

import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.graphic.Player;

public class PoundPeril extends AbstractChatOnlyMinigame {

	public PoundPeril() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp, List<Player> players) {
		disp.setDialogText("Use commands !1 - !12, captains!", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("One will hold victory...", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("but the others will slow you down!", false, -1); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Pound Peril"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

package com.dystify.marioPartyEvent.minigame;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;

public class PoundPeril extends AbstractChatOnlyMinigame {

	public PoundPeril() {
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Use commands !1 - !12, captains!", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("One will hold victory...", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
		
		disp.setDialogText("but the others will slow you down!", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); } catch (InterruptedException e) {}
	}

	@Override
	public String getName() { return "Pound Peril"; }

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

}

package com.dystify.marioPartyEvent.minigame;

import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

public class LookAway extends AbstractMinigame {

	public LookAway() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Look away text demo", false, 4000);

	}

	@Override
	public String getName() {
		return "Look Away";
	}

	@Override
	public int getTotalPizeAmt() {
		return 10;
	}

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) {
		// TODO Auto-generated method stub
		return null;
	}

}

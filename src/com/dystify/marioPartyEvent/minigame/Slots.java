package com.dystify.marioPartyEvent.minigame;

import java.util.LinkedList;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

public class Slots extends AbstractChatOnlyMinigame {
	int prizeAmt = 10;
	public Slots() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Roll the Slots and try to get\ntwo in a row, Captains\nuse !roll to spin", false, 3000);

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Slots";
	}

	@Override
	public int getTotalPizeAmt() {
		// TODO Auto-generated method stub
		return prizeAmt;
	}

	@Override
	public boolean shouldFadeOut() {
		return false;
	}
}

package com.dystify.marioPartyEvent.minigame;

import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

public abstract class AbstractBowserMinigame extends AbstractMinigame {

	public AbstractBowserMinigame() {
	}

	@Override
	protected String wrapFmtTxt(List<Player> winners) {
		return fmtWinnerTxt(winners, "And the Loser", "... HUGH? How did you all survive that!");
	}
	
	@Override
	public int getTotalPizeAmt() {
		return -30;
	}
}

package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

public class RollOfTheDiceSolo extends AbstractMinigame {

	public RollOfTheDiceSolo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("text demo for roll of the dice solo", false, 1000);
		try { Thread.sleep(1000); }catch(Exception e) {}

	}

	@Override
	public String getName() { return "Roll of the Dice (Solo)"; }

	@Override
	public int getTotalPizeAmt() { return 20; }

	@Override
	public boolean shouldFadeOut() { return false; }

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		int sum = 0;
		sum += players.get(0).getNextRoll();
		sum += players.get(0).getNextRoll();
		sum += players.get(0).getNextRoll();
		
		disp.setDialogText("Team %s, you rolled 3 times for %d\npoints total", false, 1000);
		try { Thread.sleep(1000); }catch(Exception e) {}
		
		String resp = "Since this is ";
		
		List<Player> ret = new ArrayList<>();
		if(sum < 15) {
			ret.add(players.get(0));
			resp += "less than 15, so you win!";
		} else if(sum > 15) {
			resp += "greater than 15, so you lose!";
		} else
			resp = "This is exactly 15 points!... Unfortunately\nyou still lose";
		
		disp.setDialogText(resp, false, 1000);
		try { Thread.sleep(1000); }catch(Exception e) {}
		
		return ret;
	}

}

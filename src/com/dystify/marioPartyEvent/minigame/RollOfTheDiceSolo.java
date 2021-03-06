package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.graphic.Player;

public class RollOfTheDiceSolo extends AbstractMinigame {

	public RollOfTheDiceSolo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void giveTextDemo(DisplayController disp, List<Player> players) {
		disp.setDialogText("Roll three Dice and attempt to get a total...", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
		
		disp.setDialogText("...lower than 15, " +players.get(0).getName()+ " use !roll to roll.", false, -1);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}

	}

	@Override
	public String getName() { return "Roll of the Dice (Solo)"; }

	@Override
	public int getTotalPizeAmt() { return 10; }

	@Override
	public boolean shouldFadeOut() { return false; }

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		int sum = 0;
		sum += players.get(0).getNextRoll();
		sum += players.get(0).getNextRoll();
		sum += players.get(0).getNextRoll();
		
		disp.setDialogText(String.format("Team %s, you rolled 3 times for %d points total", players.get(0).getName(), sum), false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
		
		String resp = "Since this is ";
		
		List<Player> ret = new ArrayList<>();
		if(sum < 15) {
			ret.add(players.get(0));
			resp += "less than 15, good job!";
		} else if(sum > 15) {
			resp += "greater than 15, too bad!";
		} else
			resp = "This is exactly 15 points!... Unfortunately you still lose";
		
		disp.setDialogText(resp, false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
		
		return ret;
	}

}

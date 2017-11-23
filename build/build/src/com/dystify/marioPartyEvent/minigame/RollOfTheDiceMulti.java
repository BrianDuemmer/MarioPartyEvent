package com.dystify.marioPartyEvent.minigame;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.command.RollCommand;
import com.dystify.marioPartyEvent.graphic.Player;

public class RollOfTheDiceMulti extends AbstractMinigame 
{

	public RollOfTheDiceMulti() {}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("text demo for roll of the dice", false, 1000);
		try { Thread.sleep(1000); }catch(Exception e) {}
	}

	@Override
	public String getName() {
		return "Roll of the Dice";
	}

	@Override
	public int getTotalPizeAmt() {
		return 20;
	}

	@Override
	public boolean shouldFadeOut() {
		return false;
	}

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		RollCommand defaultRoll = new RollCommand(0);
		defaultRoll.setTeamLocked(true);
		
		List<Player> ret = new LinkedList<>(players);
		do {
			Map<Player, RollCommand> trial = CommandReader.inst().pollAllForCommand(ret, defaultRoll, -1);
			
			Entry<Player, RollCommand> lowest = null;
			boolean hadDuplicates = false; // if true, there are duplicate lowest values, so we can't remove anybody this turn
			for(Entry<Player, RollCommand> e : trial.entrySet()) {
				if(lowest == null || e.getValue().getRollNum() < lowest.getValue().getRollNum()) {
					lowest = e;
					hadDuplicates = false;
				} else if (e.getValue().getRollNum() == lowest.getValue().getRollNum()) {
					hadDuplicates = true;
				}
			}
			
			if(hadDuplicates) {
				disp.setDialogText("Looks like a tie for\nlast. Nobody's out!", false, 4000);
			} else {
				ret.remove(lowest.getKey());
				disp.setDialogText(String.format("%s rolled lowest.\nThey're out!", lowest.getKey().getName()), false, 4000);
			}
		} while (ret.size() > 1);
		
		return ret;
	}

}

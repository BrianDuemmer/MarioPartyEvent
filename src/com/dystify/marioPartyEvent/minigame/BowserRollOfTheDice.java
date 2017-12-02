package com.dystify.marioPartyEvent.minigame;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.command.RollCommand;
import com.dystify.marioPartyEvent.graphic.Player;

public class BowserRollOfTheDice extends AbstractBowserMinigame {

	public BowserRollOfTheDice() {}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Roll the Dice and try to get the lowest number", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
		
		disp.setDialogText("Captains use !roll to roll", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bowser's Roll of the Dice";
	}

	@Override
	public boolean shouldFadeOut() { return false; }

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		RollCommand defaultRoll = new RollCommand(0);
		defaultRoll.setTeamLocked(true);
		
		List<Player> tmp = new LinkedList<>(players);
		do {
			Map<Player, RollCommand> trial = CommandReader.inst().pollAllForCommand(tmp, defaultRoll, -1);
			
			Entry<Player, RollCommand> highest = null;
			boolean hadDuplicates = false; // if true, there are duplicate highest values, so we can't remove anybody this turn
			for(Entry<Player, RollCommand> e : trial.entrySet()) {
				if(highest == null || e.getValue().getRollNum() > highest.getValue().getRollNum()) {
					highest = e;
					hadDuplicates = false;
				} else if (e.getValue().getRollNum() == highest.getValue().getRollNum()) {
					hadDuplicates = true;
				}
			}
			
			if(hadDuplicates) {
				disp.setDialogText("Looks like a tie for first. Nobody's out!", false, Main.dialogWaitMillis);
				try {Thread.sleep(Main.dialogWaitMillis);}catch(Exception e) {}
			} else {
				tmp.remove(highest.getKey());
				disp.setDialogText(String.format("%s rolled highest. They're out!", highest.getKey().getName()), false, Main.dialogWaitMillis);
				try {Thread.sleep(Main.dialogWaitMillis);}catch(Exception e) {}
			}
		} while (tmp.size() > 1);
		
		// select all the players not in tmp
		List<Player> ret = new LinkedList<>();
		for(Player p : players)
			if(!p.equals(tmp.get(0)))
				ret.add(p);
		
		return ret;
	}

}

package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.control2.PollResult;
import com.dystify.marioPartyEvent.graphic.Player;

public class BowserMerryGoChomp extends AbstractBowserMinigame {

	public BowserMerryGoChomp() {}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Choose a color for your team,!red, !blue, !yellow, !green", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
		
		disp.setDialogText("Whoever picks the same as Bowser wins!", false, Main.dialogWaitMillis); 
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
		
	}

	@Override
	public String getName() {
		return "Bowser's Merry Go Chomp";
	}

	@Override
	public boolean shouldFadeOut() { return false; }

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		CommandReader c = CommandReader.inst(); // just make a copy to save clutter
		List<String> cmds = Arrays.asList("!red", "!blue", "!green", "!yellow");
		Map<Player, PollResult> res = c.getDistinctPollResults(c.getAllCommandsByTeam(Main.chatVoteMillis, Filter.FILTER_COLOR, players), cmds);
		List<Player> ret = new ArrayList<>();
		
		for(Entry<Player, PollResult> e : res.entrySet()) {
			if(!e.getValue().command.equalsIgnoreCase(cmds.get(new Random().nextInt(cmds.size()))))
				ret.add(e.getKey());
		}
		
		return ret;
	}

}

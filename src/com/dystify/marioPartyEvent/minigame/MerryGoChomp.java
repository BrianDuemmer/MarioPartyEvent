package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.control2.PollResult;
import com.dystify.marioPartyEvent.control2.command.ChatCommand;
import com.dystify.marioPartyEvent.graphic.Player;
import com.dystify.marioPartyEvent.util.Util;

public class MerryGoChomp extends AbstractMinigame {

	public MerryGoChomp() {}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("merry go chomp text demo", false, 2500);
		try { Thread.sleep(2500); }catch(Exception e) {}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Merry Go Chomp";
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
		List<Player> playersLeft = new ArrayList<>(players);
		List<String> spacesLeft = Util.deepCloneStrList(Arrays.asList("!red", "!blue", "!green", "!yellow")); // wrap arrays.list in a linked list as we need it to be resizeable, and we can't do that with arrays.asList
		CommandReader c = CommandReader.inst(); // just make a copy to save clutter
		Random rng = new Random();

		while(playersLeft.size() > 1){
			// display stuff
			String txt = "Use ";
			int i=0;
			for(; i<spacesLeft.size()-1; i++)
				txt += spacesLeft.get(i)+", ";
			if(i != 0)
				txt += "and ";
			txt += spacesLeft.get(spacesLeft.size()-1);
			disp.setDialogText(txt, false, 5000);

			// poll chat and get the nominal colors
			String filter = Filter.fromCommands((spacesLeft.toArray(new String[spacesLeft.size()])));
			Map<Player, List<ChatCommand>> allCmds =c.getAllCommandsByTeam(Main.chatVoteMillis, filter , playersLeft);
			Map<Player, PollResult> trialResults = c.getDistinctPollResults(allCmds, Util.deepCloneStrList(spacesLeft)); // need to wrap spacesLeft in a new list or getDistinctPollResults will modify the original list

			// remove whoever died from the pool
			String ded = spacesLeft.remove(rng.nextInt(spacesLeft.size()));
			for(Entry<Player, PollResult> e : trialResults.entrySet()) {
				if(e.getValue().command.equalsIgnoreCase(ded)) {
					if(!playersLeft.remove(e.getKey()))
						System.err.println("Failed to remove Player " +e.getKey().getName());
					disp.setDialogText(String.format("...And %s, on %s is out!", e.getKey().getName(), ded), false, 5000);
					try { Thread.sleep(5000); } catch(Exception foo) {}
					break;
				}
			}


		} 

		return playersLeft;
	}










}
















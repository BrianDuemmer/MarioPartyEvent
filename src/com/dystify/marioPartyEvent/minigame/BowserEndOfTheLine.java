package com.dystify.marioPartyEvent.minigame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.control2.command.ChatCommand;
import com.dystify.marioPartyEvent.graphic.Player;

public class BowserEndOfTheLine extends AbstractBowserMinigame {

	public BowserEndOfTheLine() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void giveTextDemo(DisplayController disp) 
	{
		disp.setDialogText("Choose a line from the three paths,only one path is safe", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}
		
		disp.setDialogText("Use commands !left, !middle, and !right", false, Main.dialogWaitMillis);
		try { Thread.sleep(Main.dialogWaitMillis); }catch(Exception e) {}

	}

	@Override public String getName() { return "Bowser's End of the Line"; }

	@Override
	public boolean shouldFadeOut() { return false; }





	@Override protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		Random rng = new Random();
		Map<Player, List<ChatCommand>> playersLeft = null;
		boolean someoneDied = false;
		while(!someoneDied) {
			String survivingSide = "";
			switch(rng.nextInt(3)) {
				case 0: survivingSide = "left"; break;
				case 1: survivingSide = "middle"; break;
				case 2: survivingSide = "right"; break;
			}
			Map<Player, List<ChatCommand>> thisRound = getSurvivors(CommandReader.inst().getAllCommandsByTeam(Main.chatVoteMillis, Filter.FILTER_DIR_LRM, players), survivingSide);
			
			// setup a temp map, as we can't safely delete from playersLeft as we iterate over it
			Map<Player, List<ChatCommand>> tmpSurvivors = new HashMap<>();
			for(Entry<Player, List<ChatCommand>> pl : thisRound.entrySet())
				tmpSurvivors.put(pl.getKey(), new LinkedList<>());
			
			if(playersLeft == null) // can just add all
				playersLeft = new HashMap<>(thisRound);
			else { // scan through the previous survivors, and check to see who is still standing. Whoever isn't, gets knocked out of the list
				for(Entry<Player, List<ChatCommand>> pl : playersLeft.entrySet()) {
					for(ChatCommand c : pl.getValue()) {
						for(ChatCommand aSurvivor : thisRound.get(pl.getKey())) {
							if(aSurvivor.getUserId().equals(c.getUserId())) { // if they match, then this user survived and can pass, so add them to the temp list
								List<ChatCommand> thisPlayerSurvivors = tmpSurvivors.get(pl.getKey());
								thisPlayerSurvivors.add(aSurvivor);
								tmpSurvivors.put(pl.getKey(), thisPlayerSurvivors);
								break;
							}
						}
					}
				}
				playersLeft = tmpSurvivors; // copy back the survivors to the original
			}
			
			// check if anyone died
			for(Entry<Player, List<ChatCommand>> pl : playersLeft.entrySet()) {
				someoneDied = pl.getValue().isEmpty();
				if(someoneDied)
					break;
			}
			
			if(!someoneDied)
				dispSurvivorMsg(survivingSide, disp, rng);
			
		}
		
		// only pass players who still have survivors as winners
		List<Player> ret = new LinkedList<>();
		for(Entry<Player, List<ChatCommand>> pl : playersLeft.entrySet())
			if(!pl.getValue().isEmpty())
				ret.add(pl.getKey());
		
		return ret;
	}
	
	
	
	
	private Map<Player, List<ChatCommand>> getSurvivors(Map<Player, List<ChatCommand>> allThisRound, String survivingSide) 
	{
		Map<Player, List<ChatCommand>> tmp = new HashMap<>();
		for(Entry<Player, List<ChatCommand>> player : allThisRound.entrySet()) {
			tmp.put(player.getKey(), new LinkedList<>());
			for(ChatCommand c : player.getValue()) {
				if(c.getCommandText().equals("!" +survivingSide)) {
					List<ChatCommand> forthisPlayer = tmp.get(player.getKey());
					forthisPlayer.add(c);
					tmp.put(player.getKey(), forthisPlayer);
				}
			}
		}
		
		return tmp;
	}




	private void dispSurvivorMsg(String survivingSide, DisplayController disp, Random rnd) {
		int randSel = rnd.nextInt(3);
		String textTemplate = "";
		
		switch(randSel) {
			case 0: textTemplate = "Looks like the %s side is safe!"; break;
			case 1: textTemplate = "People in the %s are safe!... for now"; break;
			case 2: textTemplate = "Everyone not in the %s just got ran over!"; break;
		}
		
		disp.setDialogText(String.format(textTemplate, survivingSide), false, Main.dialogWaitMillis);
	}




}








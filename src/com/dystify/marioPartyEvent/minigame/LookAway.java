package com.dystify.marioPartyEvent.minigame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.control2.command.ChatCommand;
import com.dystify.marioPartyEvent.graphic.Player;
import com.dystify.marioPartyEvent.util.Util;

public class LookAway extends AbstractMinigame {

	public LookAway() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Pick a direction, avoid picking the\nsame direction as Bowser.", false, 4000); try { Thread.sleep(4000); } catch (InterruptedException e) {}
		disp.setDialogText(" Use commands !up,\n!down, !left, and !right!", false, 4000); try { Thread.sleep(4000); } catch (InterruptedException e) {}
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

	@Override protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		Random rng = new Random();
		Map<Player, List<ChatCommand>> playersLeft = null;
		for(int i=0; i<10; i++) {
			String survivingSide = "";
			switch(rng.nextInt(4)) {
				case 0: survivingSide = "left"; break;
				case 1: survivingSide = "right"; break;
				case 2: survivingSide = "up"; break;
				case 3: survivingSide = "down"; break;
			}
			Map<Player, List<ChatCommand>> thisRound = getSurvivors(CommandReader.inst().getAllCommandsByTeam(Main.chatVoteMillis, Filter.FILTER_DIR_UDLR, players), survivingSide);
			
			// setup a temp map, as we can't safely delete from playersLeft as we iterate over it
			Map<Player, List<ChatCommand>> tmpSurvivors = new HashMap<>();
			for(Entry<Player, List<ChatCommand>> pl : thisRound.entrySet())
				tmpSurvivors.put(pl.getKey(), new LinkedList<>());
			
			if(playersLeft == null) // can just add all
				playersLeft = new HashMap<>(thisRound);
			else { // scan through the previous survivors, and check to see who is still standing. Whoever isn't, gets knocked out of the list, in O(n^2) beauty
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
			
			if(Util.getTotalNumInMap(playersLeft) > 1) {
				dispSurvivorMsg(survivingSide, disp, rng);
			} else if(Util.getTotalNumInMap(playersLeft) == 0) // everybody lost
				break;
			
		}
		
		// only pass players who still have survivors as winners
		List<Player> ret = new LinkedList<>();
		for(Entry<Player, List<ChatCommand>> pl : playersLeft.entrySet())
			if(!pl.getValue().isEmpty())
				ret.add(pl.getKey());
		
		
		
		return ret;
	}
	
	
	
	
	
	
	private Map<Player, List<ChatCommand>> getSurvivors(Map<Player, List<ChatCommand>> allThisRound, String deadSide) 
	{
		Map<Player, List<ChatCommand>> tmp = new HashMap<>();
		for(Entry<Player, List<ChatCommand>> player : allThisRound.entrySet()) {
			tmp.put(player.getKey(), new LinkedList<>());
			for(ChatCommand c : player.getValue()) {
				if(!c.getCommandText().equals("!" +deadSide)) {
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
			case 0: textTemplate = "Looks like like bowser chose the %s side"; break;
			case 1: textTemplate = "People not in the %s are\nsafe!... for now"; break;
			case 2: textTemplate = "Everyone in the %s just \ngot knocked out!"; break;
		}
		
		disp.setDialogText(String.format(textTemplate, survivingSide), false, 4000);
	}





}

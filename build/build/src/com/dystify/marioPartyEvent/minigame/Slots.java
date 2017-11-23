package com.dystify.marioPartyEvent.minigame;

import java.util.LinkedList;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

public class Slots extends AbstractMinigame {
	int prizeAmt = 10;
	public Slots() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("slots text demo", false, 3000);

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

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		Player p = players.get(0);
		int roll1 = p.getNextRoll();
		disp.setDialogText(String.format("You rolled a %d, %s!", roll1, p.getName()), false, 4000);
		
		int roll2 = p.getNextRoll();
		int diff = Math.abs(roll2 - roll1);
		
		if(diff == 0)
			prizeAmt = 40;
		else if(diff <=2)
			prizeAmt = 25;
		else if(diff <=4)
			prizeAmt = 10;
		else {
			prizeAmt = 0;
			players = new LinkedList<Player>(); // empty the player list cuz nobody won
		}
		
		disp.setDialogText(String.format("You rolled a %d, %s! That's\na difference of %d, for %d coins", roll2, p.getName()), false, 4000);
		return players;
	}

}

package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.command.DirectionCommand;
import com.dystify.marioPartyEvent.graphic.Player;

public class TreasurePick extends AbstractMinigame {

	public TreasurePick() { }

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Choose a space, !left, !middle, or\n!right, to pick from a chest.", false, 2500);
		try { Thread.sleep(3000); }catch(Exception e) {}
		
		disp.setDialogText("Pick the right one and you win!\n", false, -1);
		try { Thread.sleep(3000); }catch(Exception e) {}
		
		disp.setDialogText("...But choose wrong, you lose!", true, 1500);
		try { Thread.sleep(2000); }catch(Exception e) {}
	}

	@Override
	public String getName() {
		return "Treasure Pick";
	}

	@Override
	public int getTotalPizeAmt() { return 15; }

	@Override
	public boolean shouldFadeOut() { return false; }

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		CommandReader.inst().pollForCommand(players, new DirectionCommand(2), -1); // give them the illusion that the great RNGsus gives a damn about their choice
		
		List<Player> ret = new ArrayList<>();
		if(new Random().nextInt(3) == 1)
			ret = players;
		
		return ret;
	}

}

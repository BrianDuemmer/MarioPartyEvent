package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.control.ChooseType;
import com.dystify.marioPartyEvent.control.Comm;
import com.dystify.marioPartyEvent.graphic.Player;

public class TreasurePick extends AbstractMinigame {

	public TreasurePick() { }

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("!Choose a number between 1 and 3,\nto pick from a chest.", false, 2500);
		try { Thread.sleep(3000); }catch(Exception e) {}
		
		disp.setDialogText("Pick the right one and you win!\n", false, -1);
		try { Thread.sleep(3000); }catch(Exception e) {}
		
		disp.setDialogText("...But choose wrong, you lose!", true, 1500);
		try { Thread.sleep(2000); }catch(Exception e) {}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Treasure Pick";
	}

	@Override
	public int getTotalPizeAmt() { return 15; }

	@Override
	public boolean shouldFadeOut() { return false; }

	@Override
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		String playersChars = players.get(0).getName().trim().toLowerCase().substring(0, 2);
		String choice = Comm.getInst().getChoice(20000, ChooseType.NUMBERTO3, playersChars);
		
		List<Player> ret = new ArrayList<>();
		try {
			int i = Integer.parseInt(choice.trim())-1;
			if(i != new Random().nextInt(3))
				ret = players;
		} catch(NumberFormatException e) {e.printStackTrace();}
		
		return ret;
	}

}

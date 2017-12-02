package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.Main;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.graphic.Player;

public class Slots extends AbstractMinigame {
	int prizeAmt = 10;
	public Slots() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void giveTextDemo(DisplayController disp) {
		disp.setDialogText("Roll the Slots and try to get two in a row!", false, Main.dialogWaitMillis);
		try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
		
		disp.setDialogText("Use !roll to spin!", false, Main.dialogWaitMillis);
		try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
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
	protected List<Player> playGameCore(List<Player> players, DisplayController disp) {
		Player p =players.get(0);
		
		int roll1 = p.getNextRoll();
		disp.setDialogText(String.format("You spun a %d, %s",roll1, p.getName()), false, Main.dialogWaitMillis);
		try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
		
		int roll2 = p.getNextRoll();
		disp.setDialogText(String.format("You spun a %d, %s",roll2, p.getName()), false, Main.dialogWaitMillis);
		try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
		
		int delta = Math.abs(roll2 - roll1);
		
		disp.setDialogText(String.format("You got a difference of %d, %s!",delta, p.getName()), false, Main.dialogWaitMillis);
		try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
		
		if(delta > 4) {
			disp.setDialogText("...That's too high to win anything.", false, Main.dialogWaitMillis);
			try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
			return new ArrayList<Player>();
		} else if(delta > 2) {
			disp.setDialogText("That's worthy of 10 coins!.", false, Main.dialogWaitMillis); try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
			prizeAmt = 10;
		} else if(delta >= 1) {
			disp.setDialogText("That's worthy of 15 coins!.", false, Main.dialogWaitMillis); try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
			prizeAmt = 15;
		} else{
			disp.setDialogText("An exact match! That's worthy of 25 coins!.", false, Main.dialogWaitMillis); try {Thread.sleep(Main.dialogWaitMillis);} catch(InterruptedException e) {}
			prizeAmt = 25;
		}
		
		return Arrays.asList(p);
	}
}

















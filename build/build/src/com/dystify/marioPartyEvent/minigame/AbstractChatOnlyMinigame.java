package com.dystify.marioPartyEvent.minigame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.command.WinnerCommand;
import com.dystify.marioPartyEvent.graphic.Player;


/**
 * This is a type of minigame that is only run in chat, without program interraction
 * @author Duemmer
 *
 */
public abstract class AbstractChatOnlyMinigame extends AbstractMinigame {

	public AbstractChatOnlyMinigame() { }


	@Override
	public List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		WinnerCommand cmd = CommandReader.inst().pollForCommand(players, new WinnerCommand(), -1);
		return new LinkedList<Player>(Arrays.asList(cmd.getWinner(players)));
	}
	
	@Override
	public int getTotalPizeAmt() {
		return 10;
	}

}

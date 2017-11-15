package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.control.Comm;
import com.dystify.marioPartyEvent.control.Command;
import com.dystify.marioPartyEvent.control.CommandType;
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
		Command c = Comm.getInst().waitOnCommand(2000000000, CommandType.WINNER_ANY);
		char winnerTxt = c.getCommandText().trim().charAt(8);
		
		Player winner = disp.getLuigi(); // luigi is da best, so he's the default 
		
		if(winnerTxt == 'm')
			winner = disp.getMario();
		else if(winnerTxt =='p')
			winner = disp.getPeach();
		else if(winnerTxt == 'y')
			winner = disp.getYoshi();
		
		 List<Player> dong = new ArrayList<>();
		 dong.add(winner);
			
		return dong;
	}

}

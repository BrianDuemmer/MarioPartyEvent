package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.graphic.Player;

public class WinnerCommand extends ChatCommand {

	public WinnerCommand() {
		// TODO Auto-generated constructor stub
	}

	public WinnerCommand(ResultSet rs) throws SQLException {
		super(rs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String commandFilter() {
		return Filter.FILTER_WINNER;
	}

	@Override
	public boolean isTeamLocked() {
		return false;
	}
	
	
	
	public Player getWinner(List<Player> players) {
		String playerChar = commandText.trim().substring(commandText.length()-1);
		
		for(Player p : players) {
			if(p.getFirstLetter().equalsIgnoreCase(playerChar))
				return p;
		}
		
		System.err.println("could not extract winner info from winner command!");
		return new Player("DEFAULT_ERR");
	}

}

package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dystify.marioPartyEvent.control2.Filter;

public class ColorChatCommand extends ChatCommand {

	public ColorChatCommand() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ColorChatCommand(ResultSet rs) throws SQLException {
		super(rs);
	}
	

	@Override
	public String commandFilter() {
		return Filter.FILTER_COLOR;
	}


	@Override
	public boolean isTeamLocked() {
		return true;
	}

}

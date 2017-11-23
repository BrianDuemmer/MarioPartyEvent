package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dystify.marioPartyEvent.control2.Filter;

public class YesNoCommand extends ChatCommand {

	public YesNoCommand() {
		// TODO Auto-generated constructor stub
	}
	
	
	public YesNoCommand(ResultSet rs) throws SQLException {
		super(rs);
	}

	@Override
	public String commandFilter() {
		return Filter.FILTER_YESNO;
	}
	
	@Override
	public boolean isTeamLocked() {
		return true;
	}

}

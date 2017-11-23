package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dystify.marioPartyEvent.control2.Filter;

public class DefaultCommand extends ChatCommand {

	public DefaultCommand() {
		// TODO Auto-generated constructor stub
	}

	public DefaultCommand(ResultSet rs) throws SQLException {
		super(rs);
	}

	@Override
	public String commandFilter() {
		return Filter.FILTER_ALL;
	}

	@Override
	public boolean isTeamLocked() {
		return false;
	}

}

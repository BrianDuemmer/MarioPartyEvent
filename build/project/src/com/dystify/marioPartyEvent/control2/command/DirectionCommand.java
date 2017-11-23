package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dystify.marioPartyEvent.control2.Filter;

public class DirectionCommand extends ChatCommand 
{
	private String filter = "";

	public DirectionCommand() {
		this(0);
	}

	/**
	 * modes: 0->all 1->udlr 2->lrm
	 * @param mode
	 */
	public DirectionCommand(int mode) {
		switch(mode) {
			case 1: filter = Filter.FILTER_DIR_UDLR; break;
			case 2: filter = Filter.FILTER_DIR_LRM; break;
			default: filter = Filter.FILTER_UDLRM; break; 
		}
	}


	public DirectionCommand(ResultSet rs) throws SQLException {
		super(rs);
	}


	@Override
	public String commandFilter() {
		return filter;
	}

	@Override
	public boolean isTeamLocked() {
		return true;
	}

}

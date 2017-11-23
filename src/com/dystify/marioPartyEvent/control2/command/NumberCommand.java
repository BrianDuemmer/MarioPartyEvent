/**
 * 
 */
package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dystify.marioPartyEvent.control2.Filter;

/**
 * @author Duemmer
 *
 */
public class NumberCommand extends ChatCommand {

	/**
	 * 
	 */
	public NumberCommand() {
		// TODO Auto-generated constructor stub
	}
	
	
	public NumberCommand(ResultSet rs) throws SQLException {
		super(rs);
	}
	
	
	@Override
	public String commandFilter() {
		return Filter.FILTER_NUM_5;
	}
	
	@Override
	public boolean isTeamLocked() {
		return true;
	}

}

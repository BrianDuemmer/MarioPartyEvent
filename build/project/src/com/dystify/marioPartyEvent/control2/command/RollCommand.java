package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dystify.marioPartyEvent.control2.Filter;

public class RollCommand extends ChatCommand
{
	private int rollNum;
	private boolean teamLocked = false;

	public RollCommand(ResultSet rs) throws SQLException 
	{
		super(rs);
		try {
			Matcher m = Pattern.compile("[0-9]+(?!.+?[0-9]+.*?\\()(?=.+?\\()").matcher(commandResponse); // extract the last number not in parenthases
			List<String> allmatches = new LinkedList<>();
			m.find();
			allmatches.add(m.group());
			rollNum = Integer.parseInt(allmatches.get(allmatches.size()-1));
		} catch(Exception e) 
		{System.err.println("Failed to extract dice roll information from the following command response: \"" +commandResponse+ "\""); e.printStackTrace();}
	}
	
	
	public RollCommand(int defaultRoll) {
		this.rollNum = defaultRoll;
	}
	
	

	public int getRollNum() {
		return rollNum;
	}


	@Override
	public String commandFilter() {
		return Filter.FILTER_ROLL;
	}


	@Override
	public boolean isTeamLocked() {
		return teamLocked;
	}


	public void setTeamLocked(boolean teamLocked) {
		this.teamLocked = teamLocked;
	}

}

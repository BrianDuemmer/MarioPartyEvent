package com.dystify.marioPartyEvent.control2.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dystify.marioPartyEvent.graphic.Player;

public abstract class ChatCommand
{
	protected long timeRun;
	protected String userId;
	protected String commandText;
	protected String commandResponse;
	protected String team;

	public ChatCommand() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Creates a new instance of a command from a resultset, from a select * from ifttt_log joined with team_minigame.team
	 * @param rs
	 * @throws SQLException
	 */
	public ChatCommand(ResultSet rs) throws SQLException {
		this.timeRun = rs.getLong("time")*1000;
		this.commandText = rs.getString("command");
		this.commandResponse = rs.getString("response");
		this.userId = rs.getString("user_id");
		this.team = rs.getString("team");
	}

	
	
	/**
	 * gets filter text for where clauses in sql, which only passes this type of command
	 * @return
	 */
	public abstract String commandFilter();
	
	/**
	 * @return true if this command should be locked to teams, false otherwise
	 */
	public abstract boolean isTeamLocked();

	public long getTimeRun() {
		return timeRun;
	}

	public String getUserId() {
		return userId;
	}

	public String getCommandText() {
		return commandText;
	}

	public String getCommandResponse() {
		return commandResponse;
	}

	public void setTimeRun(long timeRun) {
		this.timeRun = timeRun;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}

	public void setCommandResponse(String commandResponse) {
		this.commandResponse = commandResponse;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

}

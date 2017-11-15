package com.dystify.marioPartyEvent.control;

import java.util.Date;

public class Command 
{
	private String userID;
	private Date timeSent;
	private CommandType type;
	private String commandText;
	private String response;
	
	
	public Command(String userID, Date timeSent, CommandType type, String commandText, String response) {
		super();
		this.userID = userID;
		this.timeSent = timeSent;
		this.type = type;
		this.commandText = commandText;
		this.response = response;
	}


	public Command() {}
	
	
	
	
	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Date getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(Date timeSent) {
		this.timeSent = timeSent;
	}

	public CommandType getType() {
		return type;
	}

	public void setType(CommandType type) {
		this.type = type;
	}

	public String getCommandText() {
		return commandText;
	}

	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}

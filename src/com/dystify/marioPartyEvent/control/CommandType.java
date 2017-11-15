package com.dystify.marioPartyEvent.control;

/**
 * represents a chat command, for use in sql statements
 * @author Duemmer
 *
 */
public enum CommandType 
{
	ROLL("!roll"),
	WINNER_ANY("!winner%"),
	UNKNOWN(""),
	ANY("!%");
	
	private String cmd;
	
	CommandType(String cmd) {
		this.cmd = cmd;
	}
	
	@Override public String toString() {
		return cmd;
	}

	public static CommandType fromString(String string) 
	{
		for(CommandType type : values()) {
			if(type.cmd.equalsIgnoreCase(string.trim()))
				return type;
		}
		
		return UNKNOWN;
	}
}

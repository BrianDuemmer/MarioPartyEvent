package com.dystify.marioPartyEvent.control2;

public class CommandIns {
	public String command;
	public String response;
	public String userId;

	public CommandIns(String command, String response, String userId) {
		this.command = command;
		this.response = response;
		this.userId = userId;
	}

	public CommandIns(String command, String response) {
		this.command = command;
		this.response = response;
	}
}

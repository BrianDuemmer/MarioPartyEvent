package com.dystify.marioPartyEvent.control2;

/**
 * POD class to store the result of a command poll, with number of hits and the winning results
 * @author Duemmer
 *
 */
public class PollResult implements Comparable<PollResult>
{
	public String command;
	public int numHits;

	@Override
	public int compareTo(PollResult arg0) {
		return numHits - arg0.numHits;
	}

	public PollResult(String command, int numHits) {
		this.command = command;
		this.numHits = numHits;
	}

	public PollResult() {
		this.command = "";
		this.numHits = 0;
	}

}

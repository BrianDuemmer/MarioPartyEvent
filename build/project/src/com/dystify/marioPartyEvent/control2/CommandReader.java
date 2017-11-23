package com.dystify.marioPartyEvent.control2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.control2.command.ChatCommand;
import com.dystify.marioPartyEvent.control2.command.ColorChatCommand;
import com.dystify.marioPartyEvent.control2.command.DefaultCommand;
import com.dystify.marioPartyEvent.control2.command.DirectionCommand;
import com.dystify.marioPartyEvent.control2.command.NumberCommand;
import com.dystify.marioPartyEvent.control2.command.RollCommand;
import com.dystify.marioPartyEvent.control2.command.YesNoCommand;
import com.dystify.marioPartyEvent.graphic.Player;
import com.dystify.marioPartyEvent.util.Util;

public class CommandReader 
{
	private Connection db;
	private Map<Integer, ChatCommand> handledCommands = new HashMap<>(); // we processed all these commands already, so don't try to process them again
	private static CommandReader inst;


	public static CommandReader inst() {
		if(inst == null)
			inst = new CommandReader();
		return inst;
	}



	private CommandReader() 
	{
		getDb();
	}




	/**
	 * Like {@link #pollForCommand(List, ChatCommand, long) pollForCommand(...)}, but will block until each a command is 
	 * Received for each player
	 * @param players the teams to poll
	 * @param type the specific command to poll for
	 * @param timeout milliseconds before the method aborts and returns null, or -1 to never time out
	 * @return
	 */
	public <T extends ChatCommand> Map<Player, T> pollAllForCommand(List<Player> players, T type, long timeout) {
		Map<Player, T> ret = new HashMap<>();
		List<Player> left = new LinkedList<>(players); // these players still need to submit a command
		long tStart = System.currentTimeMillis();

		while(left.size() > 0 && (timeout<=0 || System.currentTimeMillis() - tStart < timeout)) {
			long playerTimeout = (tStart + timeout) - System.currentTimeMillis();
			if(timeout>0 && playerTimeout<=0) // we timed out, so break
				break;

			T command = pollForCommand(left, type, playerTimeout);
			Player fromPoll = Player.getByTeamString(command.getTeam(), players);
			ret.put(fromPoll, command);

			if(!left.remove(fromPoll)) {
				System.err.println("Failed to remove player from pollAllforCommands");
			}
		}

		for(Player p : left) // if anyone is left, add them and the default value to the return list
			ret.put(p, type);
		return ret;
	}





	/**
	 * Polls the database to get the first instance of this command that runs
	 * @param p the Player whose team to check. Only allows responses from this team
	 * @param timeout milliseconds before the method aborts and returns null, or -1 to never time out
	 * @param players the teams to whitelist
	 * @return the command that was picked up, or the same command that was given on a timeout
	 */
	@SuppressWarnings("unchecked")
	public <T extends ChatCommand> T pollForCommand(List<Player> players, T type, long timeout) 
	{
		T ret = type;
		String sql = "SELECT i.*, t.team FROM ifttt_log i "
				+ "LEFT JOIN team_minigame t ON t.user_id = i.user_id " // NOTE: left vs. inner join may cause issues later!
				+ "WHERE i.time>? AND "
				+ "(" +type.commandFilter()+ ")";
		if(ret.isTeamLocked()) // only need team clause if we want to allow or deny based on team
			sql +=" AND POSITION(t.team IN ?) >0";
		
		long tStart = System.currentTimeMillis();

		try {

			PreparedStatement ps = this.getDb().prepareStatement(sql);
			ResultSet rs = null;
			boolean found = false;
			while(!found && (timeout<=0 || System.currentTimeMillis() - tStart < timeout)) { // keep going until we time out or break out
				ps.setLong(1, Math.floorDiv(tStart, 1000)-1); // buy some wiggle room for possible timing weirdness
				if(ret.isTeamLocked())
					ps.setString(2, Player.getTeamsSearchString(players));

				rs=ps.executeQuery();
				while(!found && rs.next()) { // pick first valid result, add to handled log, break out
					if(!handledCommands.containsKey(rs.getInt("transaction_id"))) {
						ret = (T) type.getClass().getDeclaredConstructor(ResultSet.class).newInstance(rs);
						handledCommands.put(rs.getInt("transaction_id"), ret);
						found = true;
					}
				}

				Thread.sleep(500);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}






	/**
	 * Takes the results from {@link CommandReader#getAllCommandsByTeam(int, String, List) getAllCommandsByTeam} and extracts the winner for each team.
	 * Will also provide distinct results, such that no two players should have the same poll result. This orders by the number of each command
	 * each player has.
	 * 
	 * <p><b>NOTE:</b> This assumes that the correct filters were applied when generating <code>allCommands</code> such that all possible commands 
	 * are represented by <code>matches</code>
	 * @param allCommands all of the valid commands from {@link CommandReader#getAllCommandsByTeam(int, String, List) getAllCommandsByTeam}
	 * @param legalCommands specifies all the different possible distinct values for commands to expect. NOTE: should be distinct, and be the same size as players
	 */
	public Map<Player, PollResult> getDistinctPollResults(Map<Player, List<ChatCommand>> allCommandsIn, List<String> legalCommands) 
	{
		// originally designed to take command text, not whole command obj, so make a new foe input list from the input commands
		Map<Player, List<String>> allCommands = new HashMap<>();
		for(Entry<Player, List<ChatCommand>> aci : allCommandsIn.entrySet()) {
			List<String> cmds = new LinkedList<>();
			for(ChatCommand cc : aci.getValue()) {
				cmds.add(cc.getCommandText());
			}
			allCommands.put(aci.getKey(), cmds);
		}
		
		
		Map<Player, PollResult> res = new HashMap<>();
		String defaultCommand = legalCommands.get(0); // incase of errors with legalCommands being off size

		int startSize = allCommands.size();
		for(int i=0; i<startSize; i++) {
			Map<Player, PollResult> playerMaxes = new HashMap<>();

			for(Entry<Player, List<String>> player : allCommands.entrySet()) { // get the max for each player
				Map<String, Integer> playerCounts = new HashMap<>(); // number of times this player had this command

				for(String command : player.getValue()) { //check each command
					if(legalCommands.contains(command)) { // only regard the command if it's legal
						if(!playerCounts.containsKey(command)) // make sure that this command is already registered in the map before adding to the occurance count
							playerCounts.put(command, 0);
						playerCounts.put(command, playerCounts.get(command)+1);
					}
				}

				Entry<String, Integer> modeCommand = new AbstractMap.SimpleEntry<String, Integer>("", -1); // get the mode command for this player
				for(Entry<String, Integer> cmdCt : playerCounts.entrySet()) {
					if(cmdCt.getValue() > modeCommand.getValue())
						modeCommand = cmdCt;
				}
				playerMaxes.put(player.getKey(), new PollResult(modeCommand.getKey(), modeCommand.getValue()));
			}

			Entry<Player, PollResult> maxThisCycle = null;
			for(Entry<Player, PollResult> testMax : playerMaxes.entrySet()) {
				if(maxThisCycle == null || testMax.getValue().compareTo(maxThisCycle.getValue()) > 0) // set this element as the max if the current max is null or this element is greater
					maxThisCycle = testMax;
			}

			if(maxThisCycle != null && legalCommands.contains(maxThisCycle.getValue().command)) { // we have a valid max, so add to the result, remove the command and player from the pool
				res.put(maxThisCycle.getKey(), maxThisCycle.getValue());
				legalCommands.remove(maxThisCycle.getValue().command);
				allCommands.remove(maxThisCycle.getKey());
			}
		}

		// if nobody picked some of the legal commands, we may still have some players left. Just add them randomly to commands
		int i=0;
		for(Entry<Player, List<String>> missingno : allCommands.entrySet()) {
			String commandToAdd = defaultCommand;
			try { 
				commandToAdd = legalCommands.get(i); 
			}
			catch(IndexOutOfBoundsException e) {
				System.err.println("legalCommands badly formatted, more players than commands getDistinctPollResults(...)!");
			}

			res.put(missingno.getKey(), new PollResult(commandToAdd, 0));
			i++;
		}


		return res;
	}






	/**
	 * Blocks for at least <code>timeOpen</code>, and will "poll" chat for commands, processing only those that satisfy
	 * <code>filter</code>, and those who belong on the teams of <code>playersToCheck</code>.
	 * @param timeOpen milliseconds to check at minimum. Note that transaction latency may give a higher effective scanning time, but not lower
	 * @param filter the {@link Filter} pattern to check submitted commands against. Should be pre-formatted.
	 * @param playersToCheck
	 * @return all commands that satisfy the above requirements, one per user, 
	 */
	public Map<Player, List<ChatCommand>> getAllCommandsByTeam(int timeOpen, String filter, List<Player> playersToCheck) 
	{
		if(timeOpen<=0)
			throw new IllegalArgumentException("getAllCommands(...) must have a timeOpen > 0!");
		
		String sql = "SELECT t.team, i.* FROM ifttt_log i "
				+ "INNER JOIN team_minigame t ON t.user_id=i.user_id "
				+ "WHERE i.time>=? "
				+ "AND POSITION(t.team IN ?)!=0 "
				+ "AND (" +filter+")"
				+ "GROUP BY i.user_id";
		long t_start = System.currentTimeMillis();
		String plNameStr = Player.getTeamsSearchString(playersToCheck);

		Map<Player, List<ChatCommand>> allCommands = new HashMap<>();
		for(Player p : playersToCheck)
			allCommands.put(p, new LinkedList<>());

		try {
			ResultSet rs = null;
			PreparedStatement ps = this.getDb().prepareStatement(sql);
			ps.setLong(1, Math.floorDiv(t_start, 1000));
			ps.setString(2, plNameStr);

			long delayTime = t_start + timeOpen - System.currentTimeMillis();
			if(delayTime > 0) // don't bank on the database not being slow to compile the statement
				Thread.sleep(delayTime);

			rs = ps.executeQuery();
			while(rs.next()) {

				// add each command to the respective player's list
				for(Entry<Player, List<ChatCommand>> e : allCommands.entrySet()) {
					if(e.getKey().getName().toLowerCase().charAt(0) == rs.getString("team").toLowerCase().charAt(0)) {
						List<ChatCommand> cmds = e.getValue();
						if(cmds != null) {
							cmds.add(getCorrectInstance(rs));
							allCommands.put(e.getKey(), cmds);
						}
					}
				}
			}
		}catch (SQLException sqlexp) {
			System.err.println("error polling chat: ");
			sqlexp.printStackTrace();
		} catch (InterruptedException e) {}

		return allCommands;
	}



	/**
	 * Simple utility method to get the majority vote from one team
	 * @param p
	 * @param filter
	 * @param timeOpen
	 * @return
	 */
	public String voteOneTeam(Player p, String filter, int timeOpen) {
		List<ChatCommand> allCmds = getAllCommandsByTeam(timeOpen, filter, Arrays.asList(p)).get(p);
		if(allCmds.size() > 0)
			return Util.getListMode(allCmds).getCommandText();
		else { // incase nobody voted, pick the default
			System.err.println("Vote on team had no votes!");
			return "";
		}
	}
	
	
	
	
	public ChatCommand getCorrectInstance(ResultSet rs) throws SQLException 
	{
		ChatCommand c = null;
		String command = rs.getString("command");
		
		if(Filter.matchesFilter(Filter.FILTER_UDLRM, command))
			c = new DirectionCommand(rs);
		else if(Filter.matchesFilter(Filter.FILTER_COLOR, command))
			c = new ColorChatCommand(rs);
		else if(Filter.matchesFilter(Filter.FILTER_NUM_5, command))
			c = new NumberCommand(rs);
		else if(Filter.matchesFilter(Filter.FILTER_ROLL, command))
			c = new RollCommand(rs);
		else if(Filter.matchesFilter(Filter.FILTER_YESNO, command))
			c = new YesNoCommand(rs);
		else {
			System.err.println("command \"" +command+ "\" did not match any filters");
			c = new DefaultCommand(rs);
		}
		
		return c;
	}
	
	



	public Connection getDb() {
		String dbPath = "jdbc:mysql://dystify.com:3306/dystify_dystrack_server";
		try {
			if(db == null || !db.isValid(3000))
				db = DriverManager.getConnection(dbPath, "dystify_dev", "foobarbaz3001");
		} catch (SQLException e) {
			System.err.println("failed to open db!");
			e.printStackTrace();
		}
		
		return db;
	}


}
















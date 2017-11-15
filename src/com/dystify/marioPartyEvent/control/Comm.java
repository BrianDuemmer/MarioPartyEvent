package com.dystify.marioPartyEvent.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dystify.marioPartyEvent.graphic.Player;

public class Comm 
{
	private static Comm inst;
	private Connection db;
	private List<Integer> registeredCmds = new LinkedList<>();
	public boolean lockRoll;




	private Comm() {
		String dbPath = "jdbc:mysql://dystify.com:3306/dystify_dystrack_server";
		try {
			db = DriverManager.getConnection(dbPath, "dystify_dev", "foobarbaz3001");
		} catch (SQLException e) {
			System.err.println("failed to open db!");
			e.printStackTrace();
		}
	}


	public static Comm getInst() {
		if(inst == null)
			inst = new Comm();
		return inst;
	}




	/**
	 * gets all the !choose commands starting from now for time milliseconds, from certain teams
	 * and of a specific choice type
	 * @param time
	 * @param type
	 * @param teamsToCheck
	 * @return
	 */
	public List<Command> getAllChoices(int time, ChooseType type, String teamsToCheck) 
	{
		long tStart = System.currentTimeMillis();
		String sql = "SELECT " + 
				"	i.*," + 
				"	t.team " + 
				"FROM ifttt_log i INNER JOIN team_minigame t ON " + 
				"	t.user_id=i.user_id " + 
				"WHERE " + 
				"	i.time > ? AND " + 
				"	i.command = \"!choose\" AND " + 
				"	POSITION(t.team IN ?) != 0 " + 
				"GROUP BY i.user_id";
		
		List<Command> ret = new ArrayList<>();

		try {
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setLong(1, tStart / 1000);
			ps.setString(2, teamsToCheck);
			long delayTime = tStart + time - System.currentTimeMillis();

			if(delayTime > 0) // don't bank on the database not being slow to compile the statement
				Thread.sleep(delayTime);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String choice = getChoiceFromResponse(rs.getString("response"));
				if(ChooseType.isType(type, choice))
					ret.add(new Command
							(
									rs.getString("user_id"),
									new Date(rs.getLong("time") * 1000),
									CommandType.fromString(rs.getString("command")),
									rs.getString("command"),
									rs.getString("response")
									)
							);
			}
		} catch (Exception e) { e.printStackTrace(); }
		
		return ret;
	}







	public String getChoice(int timeOpen, ChooseType type, String teamsToCheck)
	{
		long tStart = System.currentTimeMillis();
		String sql = "SELECT " + 
				"	i.*," + 
				"	t.team " + 
				"FROM ifttt_log i INNER JOIN team_minigame t ON " + 
				"	t.user_id=i.user_id " + 
				"WHERE " + 
				"	i.time > ? AND " + 
				"	i.command = \"!choose\" AND " + 
				"	POSITION(t.team IN ?) != 0 " + 
				"GROUP BY i.user_id";

		try {
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setLong(1, tStart / 1000);
			ps.setString(2, teamsToCheck);
			long delayTime = tStart + timeOpen - System.currentTimeMillis();

			if(delayTime > 0) // don't bank on the database not being slow to compile the statement
				Thread.sleep(delayTime);

			ResultSet rs = ps.executeQuery();
			List<String> responses = new ArrayList<>();

			while(rs.next()) {
				String choice = getChoiceFromResponse(rs.getString("response"));
				if(ChooseType.isType(type, choice))
					responses. add(choice);
			}

			// get the predominant command
			Map<String, Integer> count = new HashMap<>();
			for(String s : responses) {
				int currCt = count.containsKey(s) ? count.get(s) + 1 : 1;
				count.put(s, currCt);
			}
			Map.Entry<String, Integer> max = Map.entry("default", 0);
			for(Map.Entry<String, Integer> foo : count.entrySet())
				if(foo.getValue() > max.getValue())
					max = foo;

			return max.getKey();
		} catch (SQLException | InterruptedException e) { e.printStackTrace(); }

		return "default";
	}
	
	
	
	
	
	
	public Player getPlayerForUser(List<Player> players, String userId) {
		String playersChars = "";
		for(Player i : players)
			playersChars += i.getName().trim().toLowerCase().charAt(0);
		
		String sql = "SELECT team FROM team_minigame WHERE user_id=? AND POSITION(team IN ?) != 0";
		try {
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, playersChars);
			
			ResultSet rs = ps.executeQuery();
			
			String team = "l";
			if(rs.next())
				team = rs.getString(1);
			else
				System.err.println("didn't find player for user " +userId);
			
			for(Player foo : players) {
				if(foo.getName().toLowerCase().charAt(0) == team.charAt(0)) {
					return foo;
				}
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		System.err.println("WARNING: returned default player in getPlayerForUser");
		return players.get(0);
	}
	





	public static String getChoiceFromResponse(String response) {
		response = response.trim().toLowerCase();
		String choice = response.substring(0, response.lastIndexOf('('));
		choice = choice.substring(choice.lastIndexOf("you chose:")+10);

		return choice.trim();

	}






	/**
	 * Will poll the database until either the timeout runs out or a new command matching the commandTypes is read
	 * @param commands all of the valid command types to search for
	 * @param timeout the number of milliseconds to wait before timing out
	 * @return the first command matching criteria
	 */
	public Command waitOnCommand(long timeout, CommandType... commands) 
	{
		long tBreak = System.currentTimeMillis() + timeout;
		long start = System.currentTimeMillis() / 1000;

		String sql = "SELECT user_id, command, time, response, transaction_id FROM ifttt_log WHERE time > ? AND (";
		for(int i=0; i<commands.length; i++) {
			if(i!= 0)
				sql += " OR ";
			sql += "command LIKE \"" +commands[i].toString() +"\"";
		}
		sql += ")";

		// poll the DB for matching commands
		try {
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setLong(1, start);
			ResultSet rs;

			while(System.currentTimeMillis() < tBreak) {

				// if we are pending on a roll
				if(lockRoll && Arrays.asList(commands).indexOf(CommandType.ROLL) >= 0)
					while(lockRoll) { Thread.sleep(500); }

				rs = ps.executeQuery();
				while(rs.next()) { // loop through hits
					if(registeredCmds.indexOf(rs.getInt("transaction_id")) == -1) {
						registeredCmds.add(rs.getInt("transaction_id"));
						Command c = new Command
								(
										rs.getString("user_id"),
										new Date(rs.getLong("time") * 1000),
										CommandType.fromString(rs.getString("command")),
										rs.getString("command"),
										rs.getString("response")
										);
						rs.close();
						ps.close();
						return c;
					}

					registeredCmds.add(rs.getInt("transaction_id"));
				}

				rs.close(); // prevent leaks if possible
				Thread.sleep(1500); // start sleeping so multiple back to back calls to waitOnCommand won't respond to the same command
			}

			ps.close();

		} catch(SQLException e) {
			e.printStackTrace();
			return new Command("", new Date(System.currentTimeMillis()), null, "SQL_ERR", "SQL error attempting to read command");
		} catch (InterruptedException e) { e.printStackTrace(); }

		return new Command("", new Date(System.currentTimeMillis()), null, "TIME_ERR", "Timed out waiting for command");
	}


	public boolean isLockRoll() {
		return lockRoll;
	}


	public void setLockRoll(boolean lockRoll) {
		this.lockRoll = lockRoll;
	}

}

















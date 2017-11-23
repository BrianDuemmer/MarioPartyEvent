package com.dystify.marioPartyEvent.util;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.control2.CommandIns;
import com.dystify.marioPartyEvent.control2.CommandReader;

import javafx.scene.image.Image;

public class Util 
{

	public static Image loadImage(String path)
	{
		Image i = null;
		try { i = new Image(loadFile(path).toString()); }
		catch(Exception e) {System.err.println("Failed to load image!"); }
		return i;
	}

	public static URL loadFile(String path)
	{
		String jarPath = "";
		try {
			jarPath = Util.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			path = new File(jarPath).getParent() + "/resource" +path;
			File f = new File(path);
			URL u = f.toURI().toURL();
			if(u == null || !f.exists())
				System.err.println("failed to find resource at \"" +path +"\"");
			else
				return u; // in a jar, the URL code sometimes gets confused when things aren't found. Only return a valid URL if we are sure nothing is amiss

		} catch (Exception e) { e.printStackTrace(); }

		return null;
	}



	/**
	 * returns the value of the dice roll
	 * @param response the raw response of the command
	 * @return the value of the dice roll, or 0 if there was an error
	 */
	public static int getDiceRoll(String response) {
		int i=0;
		try {
			String rawResp = response.substring(0, response.lastIndexOf('(')).trim();
			String numStr = rawResp.substring(rawResp.length()-2, rawResp.length()).trim();
			i = Integer.parseInt(numStr);
		} catch(Exception e) 
		{System.err.println("Failed to extract dice roll information from the following command response: \"" +response+ "\""); }

		return i;

	}


	/**
	 * Gets the first most frequently occurring value in the list
	 * @param list
	 * @return
	 */
	public static <E> E getListMode(List<E> list)
	{
		if(list.size() == 0)
			throw new IndexOutOfBoundsException("Cannot take mode of empty array!");

		Map<E, Integer> counts = new HashMap<>();
		for(E e : list) {
			if(!counts.containsKey(e))
				counts.put(e, 1);
			else
				counts.put(e, counts.get(e)+1);
		}

		int maxCt = 0;
		E maxVal = list.get(0);
		for(Entry<E, Integer> m : counts.entrySet())
			if(m.getValue() > maxCt) {
				maxCt = m.getValue();
				maxVal = m.getKey();
			}
		return maxVal;
	}



	/**
	 * Given a map of lists, gets the total number of elements in all the lists
	 * @param map
	 * @return
	 */
	public static <K, V> int getTotalNumInMap(Map<K, List<V>> map) {
		int sum = 0;
		for(Entry<K, List<V>> e : map.entrySet())
			sum += e.getValue().size();
		return sum;
	}






	public static void iftttInject(long leadMillis, CommandIns... cmds) {
		long time = System.currentTimeMillis() + leadMillis;
		String sql = "INSERT INTO ifttt_log (user_id, command, response, time) VALUES(?, ?, ?, ?)";
		try {
			Connection db = CommandReader.inst().getDb();
			PreparedStatement ps = db.prepareStatement(sql);

			for(CommandIns c : cmds) {
				ps.setString(1, c.userId);
				ps.setString(2, c.command);
				ps.setString(3, c.response);
				ps.setLong(4, time / 1000);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Inserts commands into the ifttt log dynamically. They are inserted according to team and number of each command. note that this will
	 * automatically inject unique user ids, which will be pulled from a sorted list of registered users for that team. Consider this function
	 * deterministic with respect to the contents of the team_minigame table (disregarding order)
	 * @param cmds the commands to insert. They go like this: &ltplayer, &ltcommand, number of that command&gt&gt
	 * @param leadMillisthe number of milliseconds to add to the time column of the record
	 */
	public static void smartIftttInject(Map<String, Map<CommandIns, Integer>> cmds, long leadMillis)
	{
		long timeIns = Math.floorDiv(System.currentTimeMillis() + leadMillis, 1000);
		String sql = "INSERT INTo ifttt_log (\r\n" + 
				"    command,\r\n" + 
				"    `time`,\r\n" + 
				"    response,\r\n" + 
				"    user_id\r\n" + 
				")\r\n" + 
				"\r\n" + 
				"    SELECT ?, ?, ?, user_id " + 
				"	 FROM team_minigame " + 
				"    WHERE team=? " + 
				"    ORDER BY user_id DESC " + 
				"    LIMIT ?, ?";
		try {
			PreparedStatement ps = CommandReader.inst().getDb().prepareStatement(sql);
			for(Entry<String, Map<CommandIns, Integer>> playerCmd : cmds.entrySet()) {
				int offset = 0;
				for(Entry<CommandIns, Integer> cmd : playerCmd.getValue().entrySet()) {
					ps.setString(1, cmd.getKey().command);
					ps.setLong(2, timeIns);
					ps.setString(3, cmd.getKey().response);
					ps.setString(4, String.valueOf(playerCmd.getKey().toLowerCase().trim().charAt(0)));
					ps.setInt(5, offset);
					ps.setInt(6, cmd.getValue());
					
					offset += cmd.getValue();
					
					ps.addBatch();
				}
			}
			ps.executeBatch();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	 
	
	
	
	/**
	 * Deep clones a List of strings such that modifications mader to the clone will not affect the original
	 * @param str the list to clone
	 * @return
	 */
	public static List<String> deepCloneStrList(List<String> str) {
		List<String> ret = new ArrayList<String>();
		for(String s : str)
			ret.add(new String(s.toCharArray()));
		return ret;
	}




}

























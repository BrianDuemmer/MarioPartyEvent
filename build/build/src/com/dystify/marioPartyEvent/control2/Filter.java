package com.dystify.marioPartyEvent.control2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class that contains static methods that return strings representing various types of filters for different commands
 * @author Duemmer
 *
 */
public class Filter
{
	public static final String FILTER_COLOR = "(command='!red' OR command='!blue' OR  command='!green' OR command='!yellow' OR command='!purple')";
	public static final String FILTER_DIR_UDLR = "(command='!up' OR command='!down' OR command='!left' OR command='!right')";
	public static final String FILTER_DIR_LRM = "(command='!left' OR command='!right'OR command='!middle')";
	public static final String FILTER_NUM_3 = "(command='!one' OR command='!two' OR command='!three')";
	public static final String FILTER_NUM_4 = "(command='!one' OR command='!two' OR command='!three' OR command='!four')";
	public static final String FILTER_NUM_5 = "(command='!one' OR command='!two' OR command='!three' OR command='!four' OR command='!five')";
	public static final String FILTER_YESNO = "(command='!yes' OR command='!no')";
	public static final String FILTER_UP = "command='!up'";
	public static final String FILTER_DOWN = "command='!down'";
	public static final String FILTER_LEFT = "command='!left'";
	public static final String FILTER_RIGHT = "command='!right'";
	public static final String FILTER_ALL = "1=1";
	public static final String FILTER_ROLL = "command='!roll'";
	public static final String FILTER_MIDDLE = "command='!middle'";
	public static final String FILTER_UDLRM = "(command='!up' OR command='!down' OR command='!left' OR command='!right' OR command='!middle')";
	public static final String FILTER_WINNER = "(command='!winner l' OR command='!winner m' OR command='!winner y' OR command='!winner p')";

	
	/**
	 * merges all the provided filters together, such that any of their criteria will constitute a match
	 * @param filters
	 * @return
	 */
	public static String mergeFilters(String... filters) {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<filters.length; i++) {
			if(i != 0)
				sb.append(" OR ");
			sb.append(filters[i]);
		}
		
		return sb.toString();
	}
	
	
	
	/**
	 * Checks to see if a command matches the filter string
	 * @param filter formatted filter string. column name need not be set, but can
	 * @param commandToCheck the textual command to check, including the '!'
	 * @return
	 */
	public static boolean matchesFilter(String filter, String commandToCheck) {
		Matcher m = Pattern.compile("(?<='|\\\")!.+?(?='|\\\")").matcher(filter);
		boolean matches = false;
		while(m.find())
			matches |= commandToCheck.equalsIgnoreCase(m.group());
		return matches;
	}
	
	
	
	/**
	 * takes a direction string, e.g. up, right, etc. and replaces it with the appropriate filter. ignores any "!" in the string.
	 * @param dir
	 * @return the corresponding filter, or the universal direction filter if it's not found
	 */
	public static String dirStringToFilter(String dir) {
		dir = dir.trim().toLowerCase().replaceAll("!", "");
		if(dir.equals("right"))
			return FILTER_RIGHT;
		else if(dir.equals("up"))
			return FILTER_UP;
		else if(dir.equals("down"))
			return FILTER_DOWN;
		else if(dir.equals("left"))
			return FILTER_LEFT;
		else if(dir.equals("middle"))
			return FILTER_MIDDLE;
		
		else {
			System.err.println("error, default direction filter used");
			return mergeFilters(FILTER_DIR_UDLR, FILTER_MIDDLE);
		}
	}
	
	
	
	/**
	 * Constructs a command filter based on a given set of commands
	 * @param commands
	 * @return
	 */
	public static String fromCommands(String... commands) {
		StringBuilder sb = new StringBuilder("(");
		
		for(int i=0; i<commands.length; i++) {
			sb.append("command='");
			sb.append(commands[i]);
			
			if(i != commands.length-1)
				sb.append("' OR ");
			else
				sb.append("')");
		}
		
		return sb.toString();
	}
}











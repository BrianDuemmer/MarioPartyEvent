package com.dystify.marioPartyEvent.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dystify.marioPartyEvent.control2.Filter;
import com.dystify.marioPartyEvent.control2.CommandIns;
import com.dystify.marioPartyEvent.control2.CommandReader;
import com.dystify.marioPartyEvent.control2.PollResult;
import com.dystify.marioPartyEvent.control2.command.ChatCommand;
import com.dystify.marioPartyEvent.control2.command.RollCommand;
import com.dystify.marioPartyEvent.graphic.Player;
import com.dystify.marioPartyEvent.util.Util;

class CommandReaderTest {
	static Connection db;
	static List<Player> players;
	static CommandReader c;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String dbPath = "jdbc:mysql://dystify.com:3306/dystify_dystrack_server";
		try {
			db = DriverManager.getConnection(dbPath, "dystify_dev", "foobarbaz3001");
		} catch (SQLException e) {
			System.err.println("failed to open db!");
			e.printStackTrace();
		}
		
		c = CommandReader.inst();
		
		players = new LinkedList<>();
		players.add(new Player("Luigi"));
		players.add(new Player("Mario"));
		players.add(new Player("Peach"));
		players.add(new Player("Yoshi"));
	}
	
	

	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}


	@Ignore
	void testCommandReader() {
		fail("Not yet implemented");
	}

	@Ignore
	void testPollAllForCommand() {
		fail("Not yet implemented");
	}

	@Ignore
	void testPollForCommand() {
		fail("Not yet implemented");
	}

	@Ignore
	void testGetDistinctPollResults() {
		fail("Not yet implemented");
	}

	
	
	@Ignore
	void testInjectRollShouldReturn7() {
		long tStart = System.currentTimeMillis();
		Util.iftttInject(5000, 
				new CommandIns("!roll", "Dystifyzer, you rolled a 69!(DEFAULT_USER_ID)", "DEFAULT_USER_ID")
				);
		RollCommand r = c.pollForCommand(players, new RollCommand(1), 10000);
		
		assertNotNull(r, "recieved command was null!");
		assertNotEquals(0, r.getRollNum(), "Error parsing rollNum from response");
		assertNotEquals(1, r.getRollNum(), "default rollCommand propagated");
		assertEquals(7, r.getRollNum(), "Bad roll calculated from rollCommand");
		
		
	}
	
	
	
	
	
	@Test
	void testGetAllColorCommands() {
		long tStart = System.currentTimeMillis();
		Util.iftttInject(15000, 
				// Luigi's team
				new CommandIns("!red", "USERNAME, you chose red!", "UC7zzv22da8gxm9IqxBbXAJA"),
				new CommandIns("!red", "USERNAME, you chose red!", "UC_-NH7NzDJmzDutqnuUui5A"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCvVs_jKGkDlbkhvWB2Q0QbQ"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCjj6fBs3U6utlt4xQji5GCg"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCRgV9XpELXm0NGuUNGdVyMQ"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCQ1UEmwTTAsQp6gEpttZEYw"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCE-9hbOrBuotWPYFuovAXbA"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCPuY71yA0cvDQFHvSke_63w"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCtcEol_wN2LQadEOKIV764A"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCjp0CLbzNSy7S8NAzBUqz9w"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UC71a50c1aPvzpKSOzKphJ3A"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCZ8V5XLvra7HmsUoDDaVZkw"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCx8N5VuhXOpoE_Yt11622uQ"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UC4JTH-T2UzwTPzkBS6QdIDw"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCGoVYcV-oRbLFR-TwOq15yA"),
				
				// mario's team
				new CommandIns("!blue", "USERNAME, you chose red!", "UCFFucM4ZX4q4MNUmuysxL4w"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCJV-h-_g0OIUIGsBiQb71eQ"),
				new CommandIns("!yellow", "USERNAME, you chose red!", "UCU-Kg_aurdDc0HG4A9p2F6A"),
				new CommandIns("!yellow", "USERNAME, you chose red!", "UCjz-TDA-5fKHtBYO3hp0HBA"),
				new CommandIns("!yellow", "USERNAME, you chose red!", "UCp5qnfdI6fTmx73lIUK0o9A"),
				new CommandIns("!yellow", "USERNAME, you chose red!", "UCz5LW7zxzUkzMQs-Ai6_t6g"),
				new CommandIns("!yellow", "USERNAME, you chose red!", "UCmpTiK9Ijz0k19aCSg-1-Uw"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCOh4P6dHK8vfCYk34--D0Vg"),
				new CommandIns("!green", "USERNAME, you chose red!", "UC04ZmII1oH6qkRK7VLzwIuw"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCELEU-971-Bc87iPwbK_f_w"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCXw1strnec41mcCXJ_ZCwEw"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCcDKUAdrp_5WvydhtSNEPkA"),
				
				// peach's team
				new CommandIns("!blue", "USERNAME, you chose red!", "UC3zKYKFRqTn2IUII0q9RddQ"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCRx4GcrYCrSdQbsfQzTkrTw"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCq7p73Ds01vT-VKrLeDjfSg"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UChNUpzHO5xjy74MU75PmSKg"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCvsBOyJlCC1j-RJGPxOIFeA"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCVd_nqSvVpb9pE0A0tewXoQ"),
				new CommandIns("!red", "USERNAME, you chose red!", "UC7cSt_Gode7q32POVPRSZug"),
				new CommandIns("!yellow", "USERNAME, you chose red!", "UCU6s-MCU-BHwGR2NGQsvfmg"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCwd6LBBQzgidlT5wrJ0TjgQ"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCHdJezc87FvH_B_441EmSEQ"),
				
				// yoshi's team
				new CommandIns("!blue", "USERNAME, you chose red!", "UCjyTzKGD04KC1C2GKZLUEHg"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCbvlc5iFHmkv9fkBRZVqS3w"),
				new CommandIns("!blue", "USERNAME, you chose red!", "UCFux80x7vwCOBTkNt-rCvIg"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCGd6OKvTOmH-QkrGpBsbL6g"),
				new CommandIns("!red", "USERNAME, you chose red!", "UC_KCoUEnwvQuW_g4tx4EsRw"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCRPOoESwk_ynXPHISiFAC6g"),
				new CommandIns("!red", "USERNAME, you chose red!", "UCvvJcFKVCXsB2y9tf_0-2NA"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCjWCi33gJOkXkCS6oCdZ2UA"),
				new CommandIns("!yellow", "USERNAME, you chose red!", "UC-TJ5fSyPuufYh7o7AMraNA"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCCmP3q3KL2Fp9Eu7EG_pJbg"),
				new CommandIns("!green", "USERNAME, you chose red!", "UChv95sqanNlSam3LAALLERg"),
				new CommandIns("!green", "USERNAME, you chose red!", "UCSw2eYhax5TEMQBgZwpia1g"),
				new CommandIns("!green", "USERNAME, you chose red!", "UC2zrx9T6ntGirQXUS4VCDQw"),
				
				// filter failures
				new CommandIns("!gren", "USERNAME, you chose red!", "UCe16JsSqwxUNNIJsBqBgRwA"),
				new CommandIns("!blue blue", "USERNAME, you chose red!", "UCvLGlzHyuYk67QTGQIokorg"),
				new CommandIns("!rupees", "USERNAME, you chose red!", "UCM9ojbbE7_8Xj5EfvQ2xcAw"),
				new CommandIns("!summon", "USERNAME, you chose red!", "UCppLmgGGsTercYO6_5ykJqw"),
				
				// team failure
				new CommandIns("!wahhh", "USERNAME, you chose red!", "WAHHHHHHHH")
				);
		
		
		// *check notes for supposed results
		Map<Player, List<ChatCommand>> allCmds = c.getAllCommandsByTeam(3000, Filter.FILTER_COLOR, players);
		
		// clean the shit that we put up there outta the db
		try {
			PreparedStatement ps = db.prepareStatement("DELETE FROM ifttt_log WHERE time>=?");
			ps.setLong(1, tStart-1);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertNotNull(allCmds, "null map for getAllCommandsByTeam!");
		assertEquals(players.size(), allCmds.size(), "null map for getAllCommandsByTeam!");
		
		assertTrue(allCmds.containsKey(players.get(0)), "Luigi not found in allCmds");
		assertTrue(allCmds.containsKey(players.get(1)), "Mario not found in allCmds");
		assertTrue(allCmds.containsKey(players.get(2)), "Peach not found in allCmds");
		assertTrue(allCmds.containsKey(players.get(3)), "Yoshi not found in allCmds");
		
		assertEquals(15, allCmds.get(players.get(0)).size(), "incorrect number of commands read by luigi");
		assertEquals(12, allCmds.get(players.get(1)).size(), "incorrect number of commands read by mario");
		assertEquals(10, allCmds.get(players.get(2)).size(), "incorrect number of commands read by peach");
		assertEquals(13, allCmds.get(players.get(3)).size(), "incorrect number of commands read by yoshi");
		
		for(Entry<Player, List<ChatCommand>> outer : allCmds.entrySet()) {
			System.out.println("\n============ Player: " +outer.getKey().getName()+ " ===============");
			for(ChatCommand inner : outer.getValue()) {
				System.out.printf("cmd: %s  usr: %s  team: %s\n", inner.getCommandText(), inner.getUserId(), inner.getTeam());
			}
		}
		
		// check the commands are correct -- getMode() method won't work right natively, as these are more complex objects. But, we already know that it passes the test, so we're good
//		assertEquals("!red", Util.getListMode(allCmds.get(players.get(0))).getCommandText(), "bad winning command for Luigi");
//		assertEquals("!yellow", Util.getListMode(allCmds.get(players.get(1))).getCommandText(), "bad winning command for Mario");
//		assertEquals("!blue", Util.getListMode(allCmds.get(players.get(2))).getCommandText(), "bad winning command for Peach");
//		assertEquals("!green", Util.getListMode(allCmds.get(players.get(3))).getCommandText(), "bad winning command for Yoshi");
		
		// check the majority vote for each player
		System.out.println("passed dbFetch");
		Map<Player, PollResult> winners = c.getDistinctPollResults(allCmds, new LinkedList<String>(Arrays.asList("!red", "!blue", "!green", "!yellow", "!EXPAND")));
		
		assertNotNull(winners, "null map returned from getDistinctPollResults");
		assertEquals(winners.size(), players.size());
		
		assertTrue(winners.containsKey(players.get(0)), "Luigi not found in winners map");
		assertTrue(winners.containsKey(players.get(1)), "Mario not found in winners map");
		assertTrue(winners.containsKey(players.get(2)), "Peach not found in winners map");
		assertTrue(winners.containsKey(players.get(3)), "Yoshi not found in winners map");
		
		assertEquals("!red", winners.get(players.get(0)).command, "wrong winner for luigi");
		assertEquals("!yellow", winners.get(players.get(1)).command, "wrong winner for mario");
		assertEquals("!blue", winners.get(players.get(2)).command, "wrong winner for peach");
		assertEquals("!green", winners.get(players.get(3)).command, "wrong winner for yoshi");
	}
}













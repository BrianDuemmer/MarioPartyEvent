package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.control.ChooseType;
import com.dystify.marioPartyEvent.control.Comm;
import com.dystify.marioPartyEvent.control.Command;
import com.dystify.marioPartyEvent.graphic.Player;

public class EndOfTheLine extends AbstractMinigame {

	public EndOfTheLine() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void giveTextDemo(DisplayController disp) 
	{
		disp.setDialogText("text demo for end of the line", false, 1000);
		try { Thread.sleep(1000); }catch(Exception e) {}

	}

	@Override public String getName() { return "End of the Line"; }

	@Override
	public int getTotalPizeAmt() { return 20; }

	@Override
	public boolean shouldFadeOut() { return false; }





	@Override protected List<Player> playGameCore(List<Player> players, DisplayController disp) 
	{
		String playersChars = "";
		for(Player i : players)
			playersChars += i.getName().trim().toLowerCase().charAt(0);

		// initialize, do housekeeping
		List<String> usersIntheGame = null;
		Random rng = new Random();
		int surviveTrack = 0;

		// then loop
		do {
			surviveTrack = rng.nextInt(3);
			List<String> tmpUsers = new ArrayList<>();
			tmpUsers.addAll(getSurvivors(Comm.getInst().getAllChoices(10000, ChooseType.LEFTMIDRIGHT, playersChars), surviveTrack));
			
			// send out a nice message
			disp.setDialogText(getMsgFromTrackNum(surviveTrack), false, 5000);
			
			if(usersIntheGame == null) { // this will only be true on the first time, so initialize and add all the survivors
				usersIntheGame = new ArrayList<>();
				usersIntheGame.addAll(tmpUsers);
			} else { // weed out the dead
				
				List<String> tmpForConcurrency = new ArrayList<>(usersIntheGame); // have to copy over to be able to safely remove dead users
				
				for(String user : tmpForConcurrency) {
					if(!tmpUsers.contains(user)) {
						usersIntheGame.remove(user);
					}
				}
			}
			
			System.out.println("num left: " +usersIntheGame.size());
		} while(usersIntheGame.size() > 1);

		List<Player> ret = new ArrayList<Player>();
		if(usersIntheGame.size() > 0) {
			String winnerId = usersIntheGame.get(0);
			ret.add(Comm.getInst().getPlayerForUser(players, winnerId));
		}
		
		return ret;
	}




	private String getMsgFromTrackNum(int trackNum) {
		if(trackNum == 0)
			return "Looks like the left side is safe!";
		else if(trackNum == 1)
			return "People in the middle are safe!... for now";
		else
			return "Everyone in the middle and left just\ngot ran over!";
	}




	private List<String> getSurvivors(List<Command> cmds, int surviveNum) {
		List<String> ret = new ArrayList<>();

		for(Command c : cmds) {
			String resp = Comm.getChoiceFromResponse(c.getResponse());

			boolean lived = surviveNum == 0 && resp.equalsIgnoreCase("left") ||
					surviveNum == 1 && resp.equalsIgnoreCase("middle") ||
					surviveNum == 2 && resp.equalsIgnoreCase("right");
			if(lived)
				ret.add(c.getUserID());
		}
		
		return ret;
	}


}








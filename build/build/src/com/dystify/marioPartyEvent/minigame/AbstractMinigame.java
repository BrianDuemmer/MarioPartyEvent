package com.dystify.marioPartyEvent.minigame;

import java.util.ArrayList;
import java.util.List;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.graphic.Player;

/**
 * acts as a base for all minigames
 * @author Duemmer
 *
 */
public abstract class AbstractMinigame 
{

	public AbstractMinigame() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * blocking method, will perform the entire minigame on the game thread.
	 * @param players
	 * @param disp
	 * @return
	 */
	public final List<Player> playGame(List<Player> players, DisplayController disp) 
	{
		disp.setDialogText("Now it's time for a minigame!\n", false, -1);
		try { Thread.sleep(3000); }catch (Exception e) {}
		disp.setDialogText("The minigame will be: ", true, -1);
		try { Thread.sleep(3000); }catch (Exception e) {}
		disp.setDialogText(getName() + "!", false, 2750);
		try { Thread.sleep(3000); }catch (Exception e) {}

		giveTextDemo(disp);

		disp.setDialogText("Now let's begin...", false, 2000);
		if(shouldFadeOut())
			disp.fadeTo(1, 2000);
		try { Thread.sleep(2000); }catch (Exception e) {}


		// play the game
		List<Player> winners = playGameCore(players, disp);
		if(winners == null)
			winners = new ArrayList<>();
		int prizeEach = winners.size() == 0 ? 0 : getTotalPizeAmt()/winners.size(); // last thing we need is div by 0

		// wrap up
		disp.setDialogText(fmtWinnerTxt(winners), false, 1000);
		try { Thread.sleep(2000); }catch (Exception e) {}
		if(shouldFadeOut())
			disp.fadeTo(0, 2000); // be sure to fade back out
		try { Thread.sleep(3000); }catch (Exception e) {}

		for(Player p : winners)
			p.addCoins(prizeEach);
		
		return winners;
	}




	protected abstract void giveTextDemo(DisplayController disp);
	public abstract String getName();
	public abstract int getTotalPizeAmt();
	public abstract boolean shouldFadeOut();

	/**
	 * Performs the core of minigame gameplay, after setting up initialization / intro text / fadeout
	 * @param players all the players participating in the game
	 * @param disp reference to displaycontroller, to invoke graphical events
	 * @return the list of winners. Winnings will be evenly divided among all the winners.
	 */
	protected abstract List<Player> playGameCore(List<Player> players, DisplayController disp);



	/**
	 * returns a formatted bit of text describing who won, textbox ready
	 * @param winners
	 * @return
	 */
	private String fmtWinnerTxt(List<Player> winners) {
		StringBuilder ret = new StringBuilder("And the winner");
		if(winners.size() == 1) {
			ret.append(" is: ");
			ret.append(winners.get(0).getName());
			ret.append('!');
		}
		else if(winners.size() > 1) 
		{
			ret.append("s are:");

			ret.append('\n');

			for(int i=0; i<winners.size(); i++) 
			{
				ret.append(winners.get(i).getName());
				if(i == winners.size() - 2)
					ret.append(", and ");
				else if(i< winners.size() - 2)
					ret.append(", ");
			}
			ret.append('!');
		} else {
			ret.append(" is... Nobody!\nThe V O I D must have got them...");
		}

		return ret.toString();
	}


}
















package com.dystify.marioPartyEvent.util;

import com.dystify.marioPartyEvent.graphic.Player;
import com.dystify.marioPartyEvent.space.AbstractSpace;

/**
 * Implements a handler to handle when a player passes a branch space. Serves to determine which path they take
 * @author Duemmer
 *
 */
@FunctionalInterface
public interface PathBranchHandler 
{
	/**
	 * When a player reaches a branch space, their animation will be set to idle and they will remain in 
	 * that position until this method exits. The return value of this method indicates the path to take; 
	 * true for branch, false for normal
	 * @param currSpace the current space the player is resting on
	 * @param p The Player which is waiting on this method at the branch
	 * @return true if the player should take the branch path, false if they should take the normal path
	 */
	public boolean onBranchEncountered(AbstractSpace currSpace, Player p);
}

/**
 * 
 */
package com.dystify.marioPartyEvent.control2.command;

/**
 * @author Duemmer
 *
 */
public interface CommandBase 
{
	/**
	 * Gets the specific command (e.g. !red, !up, !roll, etc.) that this command is
	 * @return
	 */
	public CommandType getCommandType();
}

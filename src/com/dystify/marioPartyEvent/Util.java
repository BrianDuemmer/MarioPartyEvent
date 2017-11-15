package com.dystify.marioPartyEvent;

import java.net.URL;

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
		URL u = Main.class.getResource(path);
		if(u == null)
			System.err.println("failed to find resource at \"" +path +"\"");
		
		return u;
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
	
	
	
	
}

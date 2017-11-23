package com.dystify.marioPartyEvent.graphic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.dystify.marioPartyEvent.DisplayController;
import com.dystify.marioPartyEvent.space.AbstractBranchSpace;
import com.dystify.marioPartyEvent.space.AbstractSpace;
import com.dystify.marioPartyEvent.space.BowserStarSpace;
import com.dystify.marioPartyEvent.util.PathBranchHandler;
import com.dystify.marioPartyEvent.util.Util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameBoard 
{
	private Image backgroundDefault;
	private Image backgroundInvStarSpace;
	private ImageView backgroundNode;
	
	private Map<Integer, AbstractSpace> spaces;
	private String name;
	
	private boolean swappedStarSpaces;
	
	
	
	
	public GameBoard(String name) throws IOException
	{
		this.name = name;
		spaces = loadMap(Util.loadFile("/board/brd_" +name+ ".csv").getPath());
		backgroundDefault = Util.loadImage("/board/brd_" +name+ "_default.jpg");
		backgroundNode = new ImageView(backgroundDefault);
	}
	
	
	
	
	/**
	 * loads a csv file containing all the spaces in a map, and their
	 * connections, branches, and types
	 * @param path
	 * @throws IOException 
	 */
	public Map<Integer, AbstractSpace> loadMap(String path) throws IOException
	{
		Map<Integer, AbstractSpace> spaces = new HashMap<>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		String row;
		while((row =reader.readLine()) != null) {
			String[] cols = row.split(",");
			try {
				int rowNum = Integer.parseInt(cols[0]);
				String spaceType = "com.dystify.marioPartyEvent.space." +cols[1].trim()+ "Space";
				double xPos = Double.parseDouble(cols[2]);
				double yPos = Double.parseDouble(cols[3]);
				int nextSpace = Integer.parseInt(cols[4]);
				int branchSpace = Integer.parseInt(cols[5]);
				
				Class<? extends AbstractSpace> clazz = Class.forName(spaceType).asSubclass(AbstractSpace.class);
				if(cols[1].trim().equalsIgnoreCase("BowserStar"))
					spaces.put(rowNum, clazz.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE).newInstance(rowNum, (int)xPos, (int)yPos, nextSpace, branchSpace, Boolean.parseBoolean(cols[6])));  
				else
					spaces.put(rowNum, clazz.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(rowNum, (int)xPos, (int)yPos, nextSpace, branchSpace));        
			} catch(Exception e) {
				System.err.println("Error loading row!");
				e.printStackTrace();
			}
		}
		reader.close();
		
		return spaces;
	}
	
	
	
	/**
	 * Advances the player along the board. Is blocking (not to jfx thread though)
	 * @param p the player to move
	 * @param numSpaces the number of spaces to move
	 */
	public void moveSpaces(Player p, int numSpaces, DisplayController disp, PathBranchHandler h) 
	{
		boolean useBranch;
		AbstractSpace s = p.getCurrSpace();
		
		for(int i=0; i<numSpaces; i++) {
			useBranch = false; // reset so it doesn't retain previous true values
			
			if(s.isBranched())
				useBranch = h.onBranchEncountered(s, p);
			
			s = useBranch ? spaces.get(s.getBranchNext()) : spaces.get(s.getNextSpace());
			p.walk(s.getXPos(), s.getYPos());
			s.onPassedCore(p, disp);
			
			if(s instanceof AbstractBranchSpace) // these don't count as spaces , so don't increment the counter
				i--;
		}
		p.setSprite(SpriteState.STILL);
		p.setCurrSpace(s);
		s.onLandedOnCore(p, disp);
	}
	
	
	
	
	
	
	public void invertStarSpaces() {
		for(Entry<Integer, AbstractSpace> e : spaces.entrySet()) {
			if(e instanceof BowserStarSpace) {
				((BowserStarSpace)e).toggleStar();
			}
		}
	}
	




	public ImageView getBackgroundNode() {
		return backgroundNode;
	}




	public Map<Integer, AbstractSpace> getSpaces() {
		return spaces;
	}
	
	
	
	
	
}












package com.dystify.marioPartyEvent.graphic;

/**
 * represents one of several states a player can be in:
 * still, moving up , moving down, etc.
 * @author Duemmer
 *
 */
public enum SpriteState {
	STILL("sprite_still.png"),
	STILL_LEFT("sprite_still_left.png"),
	STILL_RIGHT("sprite_still_right.png"),
	UP("sprite_up.gif"),
	UPRIGHT("sprite_up-right.gif"),
	RIGHT("sprite_right.gif"),
	DOWNRIGHT("sprite_down-right.gif"),
	DOWN("sprite_down.gif"),
	DOWNLEFT("sprite_down-left.gif"),
	LEFT("sprite_left.gif"),
	UPLEFT("sprite_up-left.gif");
	
	private String sprite;
	
	private SpriteState(String sprite) {
		this.sprite = sprite;
	}
	
	
	/**
	 * gets the correct sprite state based on the angle of movement.
	 * Will return STILL for any NaNs
	 */
	public static SpriteState fromAngle(double angrad) 
	{
		SpriteState state = STILL;
		
		// convert to multiples of 22.5 degrees, on 0 to 16 scale
		angrad /= Math.PI;
		angrad++;
		angrad *= 8;
		
		if(angrad <= 1 || angrad > 15)
			state = RIGHT;
		
		else if(angrad <= 3 && angrad > 1)
			state = UPRIGHT;
		
		else if(angrad <= 5 && angrad > 3)
			state = UP;
		
		else if(angrad <= 7 && angrad > 5)
			state = UPLEFT;
		
		else if(angrad <= 9 && angrad > 7)
			state = LEFT;
		
		else if(angrad <= 11 && angrad > 9)
			state = DOWNLEFT;
		
		else if(angrad <= 13 && angrad > 11)
			state = DOWN;
		
		else if(angrad <= 15 && angrad > 13)
			state = DOWNRIGHT;
		
		return state;
	}
	
	
	
	
	@Override public String toString() {
		return sprite;
	}
}






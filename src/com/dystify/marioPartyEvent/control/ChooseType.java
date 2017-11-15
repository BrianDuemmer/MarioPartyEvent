package com.dystify.marioPartyEvent.control;

import java.util.ArrayList;
import java.util.List;

public enum ChooseType 
{
	COLOR,
	NUMBERTO3,
	NUMBERTO4,
	NUMBERTO5,
	LEFTMIDRIGHT,
	DIRECTION,
	YESNO,
	RAWTEXT;


	public static List<ChooseType> fromString(String c) {
		List<ChooseType> ret = new ArrayList<>();

		// preprocess
		c = c.toLowerCase().trim();
		
		if(c.isEmpty()) // if it's empty it isn' anything
			return ret;
		
		ret.add(RAWTEXT);
		if	(
				c.equals("red") ||
				c.equals("blue") ||
				c.equals("yellow") ||
				c.equals("green")
				) { ret.add(COLOR); }
		if (
				c.equals("up") ||
				c.equals("down") ||
				c.equals("left") ||
				c.equals("right")
				) { ret.add(DIRECTION); }
		if (
				c.equals("left") ||
				c.equals("middle") ||
				c.equals("right")
				) { ret.add(ChooseType.LEFTMIDRIGHT); }
		if (
				c.equals("yes") ||
				c.equals("no")
				) { ret.add(ChooseType.YESNO); }
		
		try {
			int num = (int)Double.parseDouble(c);
			
			if(num>0 && num<=5) {
				ret.add(NUMBERTO5);
				ret.add(NUMBERTO4);
				ret.add(NUMBERTO3);
			} else if(num>0 && num<=4) {
				ret.add(NUMBERTO4);
				ret.add(NUMBERTO3);
			} else if(num>0 && num<=3) {
				ret.add(NUMBERTO3);
			}
		} catch(NumberFormatException e) {}


		return ret;
	}
	
	
	
	
	
	public static boolean isType(ChooseType type, String choice) {
		return fromString(choice).contains(type);
	}



}

















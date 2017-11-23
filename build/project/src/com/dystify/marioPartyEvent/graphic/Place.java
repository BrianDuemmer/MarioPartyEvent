package com.dystify.marioPartyEvent.graphic;

public enum Place {
	FIRST("/common/hud_1stplace.png"),
	SECOND("/common/hud_2ndplace.png"),
	THIRD("/common/hud_3rdplace.png"),
	FOURTH("/common/hud_4thplace.png");
	
	private String place;
	
	
	Place(String place) {
		this.place = place;
	}


	public String getPlace() {
		return place;
	}
	
	@Override public String toString() { return getPlace(); }
	
	public int toNum() {
		return ordinal() +1;
	}
	
	
	public static Place fromNum(int num) {
		if(num <= 1)
			return FIRST;
		 else if (num == 2) 
			 return SECOND;
		 else if(num == 3)
			 return THIRD;
		 else
			 return FOURTH;
	}
}

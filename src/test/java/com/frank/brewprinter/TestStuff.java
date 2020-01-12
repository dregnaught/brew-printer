package com.frank.brewprinter;

import org.apache.commons.text.WordUtils;

public class TestStuff {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String myString = "sdfsdf safdasf asd fas df a sdf asd fa sdf asdf";
		for(String goo : wrap(myString)) {
			System.err.println(goo);
		}
	}
	
	private static String[] wrap(String theLine) {
		String F = WordUtils.wrap(theLine, 15);
	    return centerLines(F.split(System.lineSeparator()));
	}
	
	private static String[] centerLines(String[] theLines) {
		for(int i = 0; i < theLines.length; i++) {
			int myPads = (int)((15 - theLines[i].trim().length()) / 2);
			System.err.println("pads: " + myPads);
			for(int j = 0; j < myPads; j++) {
				theLines[i] = " " + theLines[i];
			}
		}
		return theLines;
	}

}

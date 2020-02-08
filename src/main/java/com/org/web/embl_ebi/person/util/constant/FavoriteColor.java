package com.org.web.embl_ebi.person.util.constant;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This is for favorite color.
 * @since 2020
 */

public enum FavoriteColor {
	
	RED("Red"), 
	YELLOW("Yellow"), 
	GREEN("Green"), 
	ORANGE("Orange"), 
	BLUE("Blue"), 
	PINK("Pink"),
	NONE("None");

	String color;

	FavoriteColor(String color) {
		this.color = color;
	}
}
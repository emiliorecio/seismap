package com.seismap.service.parser.annotation;

public @interface EnumerationMapping {
	
	public char value();
	
	public String mapsTo();
}

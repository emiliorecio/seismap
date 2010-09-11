package com.seismap.service.parser.annotation;

public @interface EnumeratedColumn {

	public int position();
	
	public int length();
	
	public EnumerationMapping[] mappings();
}

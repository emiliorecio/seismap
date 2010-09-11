package com.seismap.service.parser.annotation;

public @interface EnumeratedColumn {

	public int position();
	
	public EnumerationMapping[] mappings();
}

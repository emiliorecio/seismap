package com.seismap.service.parser.annotation;

public @interface EnumerationColumn {

	public int position();
	
	public Class<? extends Enum<?>> enumeration();
	
	public EnumerationMapping[] mappings();
}

package com.seismap.service.parser.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)public @interface EnumeratedField {

	public int position();
	
	public int length();
	
	public EnumerationMapping[] mappings();
}

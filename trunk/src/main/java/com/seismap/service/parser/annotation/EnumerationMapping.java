package com.seismap.service.parser.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( {})
@Retention(RUNTIME)
public @interface EnumerationMapping {

	public String[] value();

	public String mapsTo();
}

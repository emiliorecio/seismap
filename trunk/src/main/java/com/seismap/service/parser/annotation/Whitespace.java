package com.seismap.service.parser.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target( {})
@Retention(RUNTIME)
public @interface Whitespace {

	public int position();

	public int length();

}

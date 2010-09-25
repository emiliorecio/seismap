package com.seismap.service.parser.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Target(TYPE)
@Retention(RUNTIME)
public @interface Entry {

	public String id();
	
	public String[] alternative() default {};

	public String[] after();

	public Whitespace[] whitespaces();
}

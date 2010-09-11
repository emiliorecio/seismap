package com.seismap.service.parser.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.seismap.service.parser.EntryType;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Entry {

	public char value();

	public EntryType type();
	
	public Whitespace[] whitespaces();
}

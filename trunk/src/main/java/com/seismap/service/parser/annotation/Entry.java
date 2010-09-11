package com.seismap.service.parser.annotation;

import com.seismap.service.parser.EntryType;

public @interface Entry {

	public char value();

	public EntryType type();
}

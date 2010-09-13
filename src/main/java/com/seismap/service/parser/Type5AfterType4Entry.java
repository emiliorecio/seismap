package com.seismap.service.parser;

import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(values = { '5' }, after = '4', type = EntryType.FOUR, whitespaces = { @Whitespace(position = 1, length = 79) })
public class Type5AfterType4Entry extends AbstractEntry {
	// unknown format
}

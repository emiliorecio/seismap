package com.seismap.service.parser;

import java.util.HashMap;
import java.util.Map;

public class Parser {

	private Map<Character, RowReader> readerRegistry = new HashMap<Character, RowReader>();

	public void registerEntryType(Class<? extends AbstractEntry> abstractEntry) {
		RowReader reader = new RowReader(abstractEntry);
		readerRegistry.put(reader.getTypeCharacter(), reader);
	}

	public Parser() {
//		registerEntryType(Type1Entry.class);
//		registerEntryType(Type2Entry.class);
		registerEntryType(Type3Entry.class);
		registerEntryType(Type4Entry.class);
		registerEntryType(Type5Entry.class);
	}

	public static void main(String[] args) {
		new Parser();
	}
}

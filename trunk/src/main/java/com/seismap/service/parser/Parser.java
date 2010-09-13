package com.seismap.service.parser;

import java.util.LinkedHashMap;
import java.util.Map;

public class Parser {

	private Map<String, RowReader> readerRegistry = new LinkedHashMap<String, RowReader>();

	public void registerEntryType(Class<? extends AbstractEntry> abstractEntry) {
		RowReader reader = new RowReader(abstractEntry);
		char[] typeKeys = reader.getTypeCharacters();
		char[] afterTypeKeys = reader.getAfterTypeCharacters();
		for (char typeKey : typeKeys) {
			for (char afterTypeKey : afterTypeKeys) {
				String keySequence = Character.toString(afterTypeKey) + typeKey;
				if (readerRegistry.containsKey(keySequence)) {
					throw new IllegalArgumentException(
							"Duplicate entry type sequence: " + keySequence);
				}
				readerRegistry.put(keySequence, reader);
			}
		}
	}

	public Parser() {
		registerEntryType(Type0Entry.class);
		registerEntryType(Type1Entry.class);
		registerEntryType(Type2Entry.class);
		registerEntryType(Type3Entry.class);
		registerEntryType(Type4Entry.class);
		registerEntryType(Type5AfterType4Entry.class);
		// registerEntryType(Type7Entry.class);
		System.out.println(readerRegistry.toString().replace(',', '\n'));
	}

	public static void main(String[] args) {
		new Parser();
	}
}

package com.seismap.service.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parser {

	private List<String> sortedIds = new ArrayList<String>();
	private Map<String, RowReader> readerRegistry = new LinkedHashMap<String, RowReader>();

	public Parser(Class<? extends AbstractEntry>[] entryClasses) {
		for (Class<? extends AbstractEntry> entryClass : entryClasses) {
			registerEntryType(entryClass);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Parser() {
		this((Class<? extends AbstractEntry>[]) new Class<?>[] {
				Type0Entry.class,
				Type1Entry.class,
				Type2Entry.class,
				Type3Entry.class,
				Type4Entry.class,
				Type6Entry.class,
				Type7Entry.class,
				TypeEEntry.class,
				TypeFEntry.class
		});

	}
	

	public void registerEntryType(Class<? extends AbstractEntry> entryClass) {
		RowReader reader = new RowReader(entryClass);
		String[] allIds = reader.getAllIds();
		String[] afterIds = reader.getAfterIds();
		for (String id : allIds) {
			for (String afterId : afterIds) {
				String idSequence = afterId + '&' + id;
				if (readerRegistry.containsKey(idSequence)) {
					throw new IllegalArgumentException(
							"Duplicate entry id sequence: " + idSequence);
				}
				readerRegistry.put(idSequence, reader);
			}
			if (!sortedIds.contains(id)) {
				sortedIds.add(id);
				Collections.sort(sortedIds, new Comparator<String>() {
					public int compare(String id1, String id2) {
						return id1.length() == id2.length() ? 0
								: (id1.length() < id2.length() ? -1 : 1);
					}
				});
			}

		}
	}
	
	public static void main(String[] args) {
		new Parser();
	}
}

package com.seismap.service.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parser {

	public static interface LogLineProvider {
		public String readLogLine(int lineNumber) throws DataProviderException;
	}

	public static interface LogEventConsumer {
		public void cosumeLogEvent(LogEvent logEvent);
	}

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
				Type0Entry.class, Type1Entry.class, Type2Entry.class,
				Type3Entry.class, Type4Entry.class, Type6Entry.class,
				Type7Entry.class, TypeEEntry.class, TypeFEntry.class,
				TypeIEntry.class });

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
								: (id1.length() > id2.length() ? -1 : 1);
					}
				});
			}
		}
	}

	private String getLineId(String line, int lineNumber)
			throws InvalidDataException {
		for (String id : sortedIds) {
			if (line.endsWith(id)) {
				return id;
			}
		}
		throw new InvalidDataException("Unknown entry type at line "
				+ lineNumber + ": " + line);
	}

	private RowReader getReader(String line, String previousLineId,
			int lineNumber) throws InvalidDataException {
		String id = getLineId(line, lineNumber);
		RowReader reader = readerRegistry.get(previousLineId + '&' + id);
		if (reader == null) {
			throw new InvalidDataException("Entry type '" + id
					+ "' is not allowed after entry type '" + previousLineId
					+ "' at line " + lineNumber + ": " + line);
		}
		return reader;
	}

	public void parse(LogLineProvider logLineProvider,
			LogEventConsumer logEventConsumer) throws InvalidDataException,
			DataProviderException {
		int lineNumber = 1;
		String line;
		String previousLineId = "^";
		Map<String, List<AbstractEntry>> entries = new HashMap<String, List<AbstractEntry>>();
		while ((line = logLineProvider.readLogLine(lineNumber)) != null) {
			if (line==" 2009 1218 2032 47.5 L                       SJA  0                            1") {
				System.out.println("AHAHHHH!!!");
			}
			RowReader reader = getReader(line, previousLineId, lineNumber);
			AbstractEntry entry = reader.read(lineNumber, line);
			List<AbstractEntry> entriesOfSameType = entries.get(reader.getId());
			if (entriesOfSameType == null) {
				entriesOfSameType = new ArrayList<AbstractEntry>();
				entries.put(reader.getId(), entriesOfSameType);
			}
			entriesOfSameType.add(entry);
			if (reader.isTerminator()) {
				LogEvent logEvent = new LogEvent(entries);
				logEventConsumer.cosumeLogEvent(logEvent);
				previousLineId = "^";
				entries = new HashMap<String, List<AbstractEntry>>();

			} else {
				previousLineId = reader.getId();
			}
			lineNumber++;
		}

	}

}

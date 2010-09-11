package com.seismap.service.parser;

public class EntryDefinitionException extends RuntimeException {

	private static final long serialVersionUID = -7433099964888782829L;

	public EntryDefinitionException(String message) {
		super(message);
	}

	public EntryDefinitionException(Throwable cause) {
		super(cause);
	}

	public EntryDefinitionException(String message, Throwable cause) {
		super(message, cause);
	}

}

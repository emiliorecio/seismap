package com.seismap.service.parser;

public class InvalidDataException extends Exception {
	
	private static final long serialVersionUID = 6921107383733571122L;

	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(Throwable cause) {
		super(cause);
	}

	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
	}

}

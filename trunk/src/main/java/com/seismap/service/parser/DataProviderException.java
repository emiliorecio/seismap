package com.seismap.service.parser;

public class DataProviderException extends Exception {

	private static final long serialVersionUID = -6282423876299975049L;

	public DataProviderException(String message) {
		super(message);
	}

	public DataProviderException(Throwable cause) {
		super(cause);
	}

	public DataProviderException(String message, Throwable cause) {
		super(message, cause);
	}

}

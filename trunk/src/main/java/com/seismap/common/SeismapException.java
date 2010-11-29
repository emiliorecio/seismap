package com.seismap.common;

public class SeismapException extends RuntimeException {

	private static final long serialVersionUID = 6521822911511307834L;

	public SeismapException(String message) {
		super(message);
	}

	public SeismapException(Throwable cause) {
		super(cause);
	}

	public SeismapException(String message, Throwable cause) {
		super(message, cause);
	}

}

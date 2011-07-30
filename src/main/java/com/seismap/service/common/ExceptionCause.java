package com.seismap.service.common;

public enum ExceptionCause {

	DUPLICATE_CATEGORY_NAME,

	DUPLICATE_MAP_NAME,
	
	NO_MAP_WITH_GIVEN_ID;

	public static enum ExceptionParameter {
		CATEGORY_ID,

		CATEGORY_NAME,
		
		MAP_ID,

		MAP_NAME,

		USER_ID,
		
		USER_EMAIL

	}

}

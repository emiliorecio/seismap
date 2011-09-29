package com.seismap.service.common;

public enum ExceptionCause {

	DUPLICATE_CATEGORY_NAME,

	DUPLICATE_MAP_NAME,

	DUPLICATE_STYLE_NAME,

	NO_EVENT_WITH_GIVEN_ID,
	
	NO_MAP_WITH_GIVEN_ID,

	NO_STYLE_WITH_GIVEN_ID,
	
	NO_USER_WITH_GIVEN_ID,
	
	UNAUTHORIZED;

	public static enum ExceptionParameter {
		CATEGORY_ID,

		CATEGORY_NAME,

		EVENT_ID,

		MAP_ID,

		MAP_NAME,

		STYLE_ID,

		STYLE_NAME,

		USER_ID,

		USER_EMAIL

	}

}

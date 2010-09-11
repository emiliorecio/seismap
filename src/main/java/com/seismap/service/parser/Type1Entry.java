package com.seismap.service.parser;

import com.seismap.service.model.DepthIndicator;
import com.seismap.service.model.DistanceIndicator;
import com.seismap.service.model.EventId;

public class Type1Entry {
	
	public static final char FIX_ORIGIN_TIME_ON = 'F';
	public static final char FIX_ORIGIN_TIME_OFF = ' ';

	public static final char DISTANCE_INDICATOR_LOCAL = 'L';
	public static final char DISTANCE_INDICATOR_REGIONAL = 'R';
	public static final char DISTANCE_INDICATOR_DISTANT = 'D';

	public static final char EVENT_ID_EXPLOSION = 'E';
	public static final char EVENT_ID_PROBABLE_EXPLOSION = 'P';
	public static final char EVENT_ID_VOLCANIC = 'V';
	public static final char EVENT_ID_PROBABLE_VOLCANIC = 'Q';

	public static final char DEPTH_INDICATOR_FIXED = 'F';
	public static final char DEPTH_INDICATOR_STARTING_VALUE = 'S';

	private int year;

	private int month;

	private int dayOfMonth;

	private boolean fixOriginTime;

	private int hour;

	private int minutes;

	private float seconds;

	private char locationModelIndicator;

	private DistanceIndicator distanceIndicator;

	private EventId eventId;

	private float latitude;

	private float longitude;

	private float depth;

	private DepthIndicator depthIndicator;
	
	private char locatingIndicator;
	
	private String 

}

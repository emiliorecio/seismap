package com.seismap.service.parser;

import javax.swing.text.Position;

import com.seismap.service.model.DepthIndicator;
import com.seismap.service.model.DistanceIndicator;
import com.seismap.service.model.EventId;
import com.seismap.service.parser.annotation.BooleanColumn;
import com.seismap.service.parser.annotation.IntegerColumn;

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

	@IntegerColumn(position=2, digits=4)
	private int year;

	@IntegerColumn(position=7, digits=2)
	private int month;

	@IntegerColumn(position=9, digits=2)
	private int dayOfMonth;

	@BooleanColumn(position=11, on='F', off=' ')
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
	
	private String hypocenterReportingAgency;

}

package com.seismap.service.parser;

import com.seismap.service.parser.annotation.BooleanField;
import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;
import com.seismap.service.parser.enumeration.DepthIndicator;
import com.seismap.service.parser.enumeration.DistanceIndicator;
import com.seismap.service.parser.enumeration.EventId;
import com.seismap.service.parser.enumeration.MagnitudeType;

@Entry(id = "1", after = { "^", "1" }, whitespaces = {
		@Whitespace(position = 1, length = 1),
		@Whitespace(position = 6, length = 1),
		@Whitespace(position = 16, length = 1) })
public class Type1Entry extends AbstractEntry {

	@IntegerField(position = 2, digits = 4)
	private int year;

	@IntegerField(position = 7, digits = 2)
	private int month;

	@IntegerField(position = 9, digits = 2)
	private int dayOfMonth;

	@BooleanField(position = 11, on = 'F', off = ' ')
	private boolean fixOriginTime;

	@IntegerField(position = 12, digits = 2)
	private int hour;

	@IntegerField(position = 14, digits = 2)
	private int minutes;

	@FloatField(position = 17, digits = 4, decimals = 1)
	private float seconds;

	@CharacterField(position = 21)
	private char locationModelIndicator;

	@EnumeratedField(position = 22, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "LOCAL"),
			@EnumerationMapping(value = "R", mapsTo = "REGIONAL"),
			@EnumerationMapping(value = "D", mapsTo = "DISTANT") })
	private DistanceIndicator distanceIndicator;

	@EnumeratedField(position = 23, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "UNSPECIFIED"),
			@EnumerationMapping(value = "E", mapsTo = "EXPLOSION"),
			@EnumerationMapping(value = "P", mapsTo = "PROBABLE_EXPLOSION"),
			@EnumerationMapping(value = "V", mapsTo = "VOLCANIC"),
			@EnumerationMapping(value = "Q", mapsTo = "PROBABLE_VOLCANIC"),
			@EnumerationMapping(value = "l", mapsTo = "L"),
			@EnumerationMapping(value = "*", mapsTo = "ASTERISK") })
	private EventId eventId;

	@FloatField(position = 24, digits = 7, decimals = 3)
	private Float latitude;

	@FloatField(position = 31, digits = 8, decimals = 3)
	private Float longitude;

	@FloatField(position = 39, digits = 5, decimals = 1)
	private Float depth;

	@EnumeratedField(position = 44, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "UNSPECIFIED"),
			@EnumerationMapping(value = "F", mapsTo = "FIXED"),
			@EnumerationMapping(value = "S", mapsTo = "STARTING_VALUE") })
	private DepthIndicator depthIndicator;

	@CharacterField(position = 45)
	private char locatingIndicator;

	@StringField(position = 46, length = 3)
	private String hypocenterReportingAgency;

	@IntegerField(position = 49, digits = 3)
	private Integer numberOfStationsUsed;

	@FloatField(position = 52, digits = 4, decimals = 1)
	private Float rmsOfTimeResiduals;

	@FloatField(position = 56, digits = 4, decimals = 1)
	private Float magnitude1;

	@EnumeratedField(position = 60, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "BLANK"),
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = { "B", "b" }, mapsTo = "MB"),
			@EnumerationMapping(value = { "S", "s" }, mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC") })
	private MagnitudeType magnitude1Type;

	@StringField(position = 61, length = 3)
	private String magnitude1ReportingAgency;

	@FloatField(position = 64, digits = 4, decimals = 1)
	private Float magnitude2;

	@EnumeratedField(position = 68, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "BLANK"),
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = { "B", "b" }, mapsTo = "MB"),
			@EnumerationMapping(value = { "S", "s" }, mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC") })
	private MagnitudeType magnitude2Type;

	@StringField(position = 69, length = 3)
	private String magnitude2ReportingAgency;

	@FloatField(position = 72, digits = 4, decimals = 1)
	private Float magnitude3;

	@EnumeratedField(position = 76, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "BLANK"),
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = { "B", "b" }, mapsTo = "MB"),
			@EnumerationMapping(value = { "S", "s" }, mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC") })
	private MagnitudeType magnitude3Type;

	@StringField(position = 77, length = 3)
	private String magnitude3ReportingAgency;

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public boolean isFixOriginTime() {
		return fixOriginTime;
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public float getSeconds() {
		return seconds;
	}

	public char getLocationModelIndicator() {
		return locationModelIndicator;
	}

	public DistanceIndicator getDistanceIndicator() {
		return distanceIndicator;
	}

	public EventId getEventId() {
		return eventId;
	}

	public Float getLatitude() {
		return latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public Float getDepth() {
		return depth;
	}

	public DepthIndicator getDepthIndicator() {
		return depthIndicator;
	}

	public char getLocatingIndicator() {
		return locatingIndicator;
	}

	public String getHypocenterReportingAgency() {
		return hypocenterReportingAgency;
	}

	public Integer getNumberOfStationsUsed() {
		return numberOfStationsUsed;
	}

	public Float getRmsOfTimeResiduals() {
		return rmsOfTimeResiduals;
	}

	public Float getMagnitude1() {
		return magnitude1;
	}

	public MagnitudeType getMagnitude1Type() {
		return magnitude1Type;
	}

	public String getMagnitude1ReportingAgency() {
		return magnitude1ReportingAgency;
	}

	public Float getMagnitude2() {
		return magnitude2;
	}

	public MagnitudeType getMagnitude2Type() {
		return magnitude2Type;
	}

	public String getMagnitude2ReportingAgency() {
		return magnitude2ReportingAgency;
	}

	public Float getMagnitude3() {
		return magnitude3;
	}

	public MagnitudeType getMagnitude3Type() {
		return magnitude3Type;
	}

	public String getMagnitude3ReportingAgency() {
		return magnitude3ReportingAgency;
	}

}

package com.seismap.service.parser;

import com.seismap.service.model.DepthIndicator;
import com.seismap.service.model.DistanceIndicator;
import com.seismap.service.model.EventId;
import com.seismap.service.model.MagnitudeType;
import com.seismap.service.parser.annotation.BooleanColumn;
import com.seismap.service.parser.annotation.CharacterColumn;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedColumn;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatColumn;
import com.seismap.service.parser.annotation.IntegerColumn;
import com.seismap.service.parser.annotation.StringColumn;

@Entry(value = '1', type = EntryType.ONE)
public class Type1Entry {

	@IntegerColumn(position = 2, digits = 4)
	private int year;

	@IntegerColumn(position = 7, digits = 2)
	private int month;

	@IntegerColumn(position = 9, digits = 2)
	private int dayOfMonth;

	@BooleanColumn(position = 11, on = 'F', off = ' ')
	private boolean fixOriginTime;

	@IntegerColumn(position = 12, digits = 2)
	private int hour;

	@IntegerColumn(position = 14, digits = 2)
	private int minutes;

	@FloatColumn(position = 17, integerDigits = 4, decimalDigits = 1)
	private float seconds;

	@CharacterColumn(position = 21)
	private char locationModelIndicator;

	@EnumeratedColumn(position = 22, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "LOCAL"),
			@EnumerationMapping(value = "R", mapsTo = "REGIONAL"),
			@EnumerationMapping(value = "D", mapsTo = "DISTANT") })
	private DistanceIndicator distanceIndicator;

	@EnumeratedColumn(position = 23, length = 1, mappings = {
			@EnumerationMapping(value = "E", mapsTo = "EXPLOSION"),
			@EnumerationMapping(value = "P", mapsTo = "PROBABLE_EXPLOSION"),
			@EnumerationMapping(value = "V", mapsTo = "VOLCANIC"),
			@EnumerationMapping(value = "Q", mapsTo = "PROBABLE_VOLCANIC") })
	private EventId eventId;

	@FloatColumn(position = 24, integerDigits = 7, decimalDigits = 3)
	private float latitude;

	@FloatColumn(position = 31, integerDigits = 8, decimalDigits = 3)
	private float longitude;

	@FloatColumn(position = 39, integerDigits = 5, decimalDigits = 1)
	private float depth;

	@EnumeratedColumn(position = 44, length = 1, mappings = {
			@EnumerationMapping(value = "F", mapsTo = "FIXED"),
			@EnumerationMapping(value = "S", mapsTo = "STARTING_VALUE") })
	private DepthIndicator depthIndicator;

	@CharacterColumn(position = 45)
	private char locatingIndicator;

	@StringColumn(position = 46, length = 3)
	private String hypocenterReportingAgency;

	@IntegerColumn(position = 49, digits = 3)
	private int numberOfStationsUsed;

	@FloatColumn(position = 52, integerDigits = 2, decimalDigits = 1)
	private String rmsOfTimeResiduals;

	@FloatColumn(position = 56, integerDigits = 4, decimalDigits = 1)
	private float magnitude1;

	@EnumeratedColumn(position = 60, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = "B", mapsTo = "MB"),
			@EnumerationMapping(value = "S", mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC"), })
	private MagnitudeType magnitude1Type;

	@StringColumn(position = 61, length = 3)
	private String magnitude1ReportingAgency;

	@FloatColumn(position = 64, integerDigits = 4, decimalDigits = 1)
	private float magnitude2;

	@EnumeratedColumn(position = 68, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = "B", mapsTo = "MB"),
			@EnumerationMapping(value = "S", mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC"), })
	private MagnitudeType magnitude2Type;

	@StringColumn(position = 69, length = 3)
	private String magnitude2ReportingAgency;

	@FloatColumn(position = 72, integerDigits = 4, decimalDigits = 1)
	private float magnitude3;

	@EnumeratedColumn(position = 76, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = "B", mapsTo = "MB"),
			@EnumerationMapping(value = "S", mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC"), })
	private MagnitudeType magnitude3Type;

	@StringColumn(position = 77, length = 3)
	private String magnitude3ReportingAgency;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public boolean isFixOriginTime() {
		return fixOriginTime;
	}

	public void setFixOriginTime(boolean fixOriginTime) {
		this.fixOriginTime = fixOriginTime;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public float getSeconds() {
		return seconds;
	}

	public void setSeconds(float seconds) {
		this.seconds = seconds;
	}

	public char getLocationModelIndicator() {
		return locationModelIndicator;
	}

	public void setLocationModelIndicator(char locationModelIndicator) {
		this.locationModelIndicator = locationModelIndicator;
	}

	public DistanceIndicator getDistanceIndicator() {
		return distanceIndicator;
	}

	public void setDistanceIndicator(DistanceIndicator distanceIndicator) {
		this.distanceIndicator = distanceIndicator;
	}

	public EventId getEventId() {
		return eventId;
	}

	public void setEventId(EventId eventId) {
		this.eventId = eventId;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public DepthIndicator getDepthIndicator() {
		return depthIndicator;
	}

	public void setDepthIndicator(DepthIndicator depthIndicator) {
		this.depthIndicator = depthIndicator;
	}

	public char getLocatingIndicator() {
		return locatingIndicator;
	}

	public void setLocatingIndicator(char locatingIndicator) {
		this.locatingIndicator = locatingIndicator;
	}

	public String getHypocenterReportingAgency() {
		return hypocenterReportingAgency;
	}

	public void setHypocenterReportingAgency(String hypocenterReportingAgency) {
		this.hypocenterReportingAgency = hypocenterReportingAgency;
	}

	public int getNumberOfStationsUsed() {
		return numberOfStationsUsed;
	}

	public void setNumberOfStationsUsed(int numberOfStationsUsed) {
		this.numberOfStationsUsed = numberOfStationsUsed;
	}

	public String getRmsOfTimeResiduals() {
		return rmsOfTimeResiduals;
	}

	public void setRmsOfTimeResiduals(String rmsOfTimeResiduals) {
		this.rmsOfTimeResiduals = rmsOfTimeResiduals;
	}

	public float getMagnitude1() {
		return magnitude1;
	}

	public void setMagnitude1(float magnitude1) {
		this.magnitude1 = magnitude1;
	}

	public MagnitudeType getMagnitude1Type() {
		return magnitude1Type;
	}

	public void setMagnitude1Type(MagnitudeType magnitude1Type) {
		this.magnitude1Type = magnitude1Type;
	}

	public String getMagnitude1ReportingAgency() {
		return magnitude1ReportingAgency;
	}

	public void setMagnitude1ReportingAgency(String magnitude1ReportingAgency) {
		this.magnitude1ReportingAgency = magnitude1ReportingAgency;
	}

	public float getMagnitude2() {
		return magnitude2;
	}

	public void setMagnitude2(float magnitude2) {
		this.magnitude2 = magnitude2;
	}

	public MagnitudeType getMagnitude2Type() {
		return magnitude2Type;
	}

	public void setMagnitude2Type(MagnitudeType magnitude2Type) {
		this.magnitude2Type = magnitude2Type;
	}

	public String getMagnitude2ReportingAgency() {
		return magnitude2ReportingAgency;
	}

	public void setMagnitude2ReportingAgency(String magnitude2ReportingAgency) {
		this.magnitude2ReportingAgency = magnitude2ReportingAgency;
	}

	public float getMagnitude3() {
		return magnitude3;
	}

	public void setMagnitude3(float magnitude3) {
		this.magnitude3 = magnitude3;
	}

	public MagnitudeType getMagnitude3Type() {
		return magnitude3Type;
	}

	public void setMagnitude3Type(MagnitudeType magnitude3Type) {
		this.magnitude3Type = magnitude3Type;
	}

	public String getMagnitude3ReportingAgency() {
		return magnitude3ReportingAgency;
	}

	public void setMagnitude3ReportingAgency(String magnitude3ReportingAgency) {
		this.magnitude3ReportingAgency = magnitude3ReportingAgency;
	}

}

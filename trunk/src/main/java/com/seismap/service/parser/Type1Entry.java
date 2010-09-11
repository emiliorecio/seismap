package com.seismap.service.parser;

import com.seismap.service.model.DepthIndicator;
import com.seismap.service.model.DistanceIndicator;
import com.seismap.service.model.EventId;
import com.seismap.service.model.MagnitudeType;
import com.seismap.service.parser.annotation.BooleanField;
import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;

@Entry(value = '1', type = EntryType.ONE)
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
			@EnumerationMapping(value = "E", mapsTo = "EXPLOSION"),
			@EnumerationMapping(value = "P", mapsTo = "PROBABLE_EXPLOSION"),
			@EnumerationMapping(value = "V", mapsTo = "VOLCANIC"),
			@EnumerationMapping(value = "Q", mapsTo = "PROBABLE_VOLCANIC") })
	private EventId eventId;

	@FloatField(position = 24, digits = 7, decimals = 3)
	private float latitude;

	@FloatField(position = 31, digits = 8, decimals = 3)
	private float longitude;

	@FloatField(position = 39, digits = 5, decimals = 1)
	private float depth;

	@EnumeratedField(position = 44, length = 1, mappings = {
			@EnumerationMapping(value = "F", mapsTo = "FIXED"),
			@EnumerationMapping(value = "S", mapsTo = "STARTING_VALUE") })
	private DepthIndicator depthIndicator;

	@CharacterField(position = 45)
	private char locatingIndicator;

	@StringField(position = 46, length = 3)
	private String hypocenterReportingAgency;

	@IntegerField(position = 49, digits = 3)
	private int numberOfStationsUsed;

	@FloatField(position = 52, digits = 4, decimals = 1)
	private float rmsOfTimeResiduals;

	@FloatField(position = 56, digits = 4, decimals = 1)
	private float magnitude1;

	@EnumeratedField(position = 60, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = "B", mapsTo = "MB"),
			@EnumerationMapping(value = "S", mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC"), })
	private MagnitudeType magnitude1Type;

	@StringField(position = 61, length = 3)
	private String magnitude1ReportingAgency;

	@FloatField(position = 64, digits = 4, decimals = 1)
	private float magnitude2;

	@EnumeratedField(position = 68, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = "B", mapsTo = "MB"),
			@EnumerationMapping(value = "S", mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC"), })
	private MagnitudeType magnitude2Type;

	@StringField(position = 69, length = 3)
	private String magnitude2ReportingAgency;

	@FloatField(position = 72, digits = 4, decimals = 1)
	private float magnitude3;

	@EnumeratedField(position = 76, length = 1, mappings = {
			@EnumerationMapping(value = "L", mapsTo = "ML"),
			@EnumerationMapping(value = "B", mapsTo = "MB"),
			@EnumerationMapping(value = "S", mapsTo = "MS"),
			@EnumerationMapping(value = "W", mapsTo = "MW"),
			@EnumerationMapping(value = "G", mapsTo = "MBLG"),
			@EnumerationMapping(value = "C", mapsTo = "MC"), })
	private MagnitudeType magnitude3Type;

	@StringField(position = 77, length = 3)
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

	public float getRmsOfTimeResiduals() {
		return rmsOfTimeResiduals;
	}

	public void setRmsOfTimeResiduals(float rmsOfTimeResiduals) {
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

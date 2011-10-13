package com.seismap.service.parser;

import com.seismap.service.parser.annotation.BooleanField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;
import com.seismap.service.parser.enumeration.Component;
import com.seismap.service.parser.enumeration.InstrumentType;
import com.seismap.service.parser.enumeration.Motion;
import com.seismap.service.parser.enumeration.Phase;
import com.seismap.service.parser.enumeration.QualityIndicator;
import com.seismap.service.parser.enumeration.WeightingIndicator;

@Entry(id = "4", alternative = " ", after = { "1", "2", "3", "4", "6", "7",
		"E", "I" }, whitespaces = { @Whitespace(position = 1, length = 1),
		@Whitespace(position = 9, length = 1),
		@Whitespace(position = 18, length = 1),
		@Whitespace(position = 29, length = 1),
		@Whitespace(position = 41, length = 1),
		@Whitespace(position = 46, length = 1),
		@Whitespace(position = 52, length = 1),
		@Whitespace(position = 76, length = 1) })
public class Type4Entry extends AbstractEntry {

	@StringField(position = 2, length = 5)
	private String stationName;

	@EnumeratedField(position = 7, length = 1, mappings = {
			@EnumerationMapping(value = "B", mapsTo = "B"),
			@EnumerationMapping(value = "P", mapsTo = "P"),
			@EnumerationMapping(value = "S", mapsTo = "SP"),
			@EnumerationMapping(value = "I", mapsTo = "IP"),
			@EnumerationMapping(value = "L", mapsTo = "LP"),
			@EnumerationMapping(value = "H", mapsTo = "H"),
			@EnumerationMapping(value = "E", mapsTo = "E") })
	private InstrumentType instrumentType;

	@EnumeratedField(position = 8, length = 1, mappings = {
			@EnumerationMapping(value = "Z", mapsTo = "Z"),
			@EnumerationMapping(value = "N", mapsTo = "N"),
			@EnumerationMapping(value = "E", mapsTo = "E"),
			@EnumerationMapping(value = "T", mapsTo = "T"),
			@EnumerationMapping(value = "R", mapsTo = "R") })
	private Component component;

	@EnumeratedField(position = 10, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "BLANK"),
			@EnumerationMapping(value = "I", mapsTo = "I"),
			@EnumerationMapping(value = "E", mapsTo = "E") })
	private QualityIndicator qualityIndicator;

	@EnumeratedField(position = 11, length = 4, mappings = {
			@EnumerationMapping(value = "    ", mapsTo = "BLANK"),
			@EnumerationMapping(value = { "PN  ", "Pn  " }, mapsTo = "PN"),
			@EnumerationMapping(value = { "PG  ", "Pg  " }, mapsTo = "PG"),
			@EnumerationMapping(value = "LG  ", mapsTo = "LG"),
			@EnumerationMapping(value = "P   ", mapsTo = "P"),
			@EnumerationMapping(value = "S   ", mapsTo = "S"),
			@EnumerationMapping(value = { "AML ", " AML" }, mapsTo = "AML"),
			@EnumerationMapping(value = "PN3 ", mapsTo = "PN3"),
			@EnumerationMapping(value = "IAML", mapsTo = "IAML"),
			@EnumerationMapping(value = "AMP ", mapsTo = "AMP"),
			@EnumerationMapping(value = "AMB ", mapsTo = "AMB"),
			@EnumerationMapping(value = "AMSN", mapsTo = "AMSN"),
			@EnumerationMapping(value = "Sn  ", mapsTo = "SN"),
			@EnumerationMapping(value = "AMSG", mapsTo = "AMSG"),
			@EnumerationMapping(value = "AMPG", mapsTo = "AMPG"),
			@EnumerationMapping(value = "IAmb", mapsTo = "IAMB") })
	private Phase phase;

	@EnumeratedField(position = 15, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "WEIGHT_100_PERCENT"),
			@EnumerationMapping(value = "0", mapsTo = "WEIGHT_100_PERCENT"),
			@EnumerationMapping(value = "1", mapsTo = "WEIGHT_75_PERCENT"),
			@EnumerationMapping(value = "2", mapsTo = "WEIGHT_50_PERCENT"),
			@EnumerationMapping(value = "3", mapsTo = "WEIGHT_25_PERCENT"),
			@EnumerationMapping(value = "4", mapsTo = "WEIGHT_0_PERCENT"),
			@EnumerationMapping(value = "9", mapsTo = "NO_WEIGHT") })
	private WeightingIndicator weightingIndicator;

	@BooleanField(position = 16, off = ' ', on = 'A')
	private boolean automaticPick;

	@EnumeratedField(position = 17, length = 1, mappings = {
			@EnumerationMapping(value = " ", mapsTo = "BLANK"),
			@EnumerationMapping(value = "C", mapsTo = "C"),
			@EnumerationMapping(value = "D", mapsTo = "D") })
	private Motion firstMotion;

	@IntegerField(position = 19, digits = 2)
	private int hour;

	@IntegerField(position = 21, digits = 2)
	private int minutes;

	@FloatField(position = 23, digits = 6, decimals = 0)
	private float seconds;

	@IntegerField(position = 30, digits = 4)
	private Integer duration;

	@FloatField(position = 34, digits = 7, decimals = 1)
	private Float amplitude;

	@FloatField(position = 42, digits = 4, decimals = 0)
	private Float periodSeconds;

	@FloatField(position = 47, digits = 5, decimals = 0)
	private Float directionOfAproach;

	@FloatField(position = 53, digits = 4, decimals = 0)
	private Float phaseVelocity;

	@FloatField(position = 57, digits = 4, decimals = 0)
	private Float angleOfIncidence;

	@IntegerField(position = 61, digits = 3)
	private Integer azimuthResidual;

	@FloatField(position = 64, digits = 5, decimals = 1)
	private Float travelTimeResidual;

	@IntegerField(position = 69, digits = 2)
	private Integer weight;

	@FloatField(position = 71, digits = 5, decimals = 0)
	private Float epicentralDistance;

	@IntegerField(position = 77, digits = 3)
	private Integer azimuthAtSource;

	public String getStationName() {
		return stationName;
	}

	public InstrumentType getInstrumentType() {
		return instrumentType;
	}

	public Component getComponent() {
		return component;
	}

	public QualityIndicator getQualityIndicator() {
		return qualityIndicator;
	}

	public Phase getPhase() {
		return phase;
	}

	public WeightingIndicator getWeightingIndicator() {
		return weightingIndicator;
	}

	public boolean getAutomaticPick() {
		return automaticPick;
	}

	public Motion getFirstMotion() {
		return firstMotion;
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

	public Integer getDuration() {
		return duration;
	}

	public Float getAmplitude() {
		return amplitude;
	}

	public Float getPeriodSeconds() {
		return periodSeconds;
	}

	public Float getDirectionOfAproach() {
		return directionOfAproach;
	}

	public Float getPhaseVelocity() {
		return phaseVelocity;
	}

	public Float getAngleOfIncidence() {
		return angleOfIncidence;
	}

	public Integer getAzimuthResidual() {
		return azimuthResidual;
	}

	public Float getTravelTimeResidual() {
		return travelTimeResidual;
	}

	public Integer getWeight() {
		return weight;
	}

	public Float getEpicentralDistance() {
		return epicentralDistance;
	}

	public Integer getAzimuthAtSource() {
		return azimuthAtSource;
	}

}

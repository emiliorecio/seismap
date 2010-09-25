package com.seismap.service.parser;

import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.EnumeratedField;
import com.seismap.service.parser.annotation.EnumerationMapping;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;
import com.seismap.service.parser.enumeration.InstrumentType;
import com.seismap.service.parser.enumeration.Phase;

@Entry(id = "4", alternative = " ", after = { "2", "3", "4" }, whitespaces = {
		@Whitespace(position = 1, length = 1),
		@Whitespace(position = 13, length = 1),
		@Whitespace(position = 14, length = 1),
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
			@EnumerationMapping(value = "S", mapsTo = "SP"),
			@EnumerationMapping(value = "I", mapsTo = "IP"),
			@EnumerationMapping(value = "L", mapsTo = "LP") })
	private InstrumentType instrumentType;

	@CharacterField(position = 8)
	private char component;

	@CharacterField(position = 9)
	private char epicentral;

	@CharacterField(position = 10)
	private char qualityIndicator;

	@EnumeratedField(position = 11, length = 2, mappings = {
			@EnumerationMapping(value = "PN", mapsTo = "PN"),
			@EnumerationMapping(value = "PG", mapsTo = "PG"),
			@EnumerationMapping(value = "LG", mapsTo = "LG"),
			@EnumerationMapping(value = "P ", mapsTo = "P"),
			@EnumerationMapping(value = "S ", mapsTo = "S") })
	private Phase phase;

	@IntegerField(position = 15, digits = 1)
	private int weightingIndicator;

	@CharacterField(position = 16)
	private char automaticPick;

	@CharacterField(position = 17)
	private char firstMotion;

	@IntegerField(position = 19, digits = 2)
	private int hour;

	@IntegerField(position = 21, digits = 2)
	private int minutes;

	@FloatField(position = 23, digits = 6, decimals = 0)
	private float seconds;

	@IntegerField(position = 30, digits = 4)
	private int duration;

	@FloatField(position = 34, digits = 7, decimals = 1)
	private float aplitude;

	@FloatField(position = 42, digits = 4, decimals = 0)
	private float periodSeconds;

	@FloatField(position = 47, digits = 5, decimals = 0)
	private float directionOfAproach;

	@FloatField(position = 53, digits = 4, decimals = 0)
	private float phaseVelocity;

	@FloatField(position = 57, digits = 4, decimals = 0)
	private float angleOfIncidence;

	@IntegerField(position = 61, digits = 3)
	private int azimuthResidual;

	@FloatField(position = 64, digits = 5, decimals = 1)
	private float travelTimeResidual;

	@IntegerField(position = 69, digits = 2)
	private int weight;

	@FloatField(position = 71, digits = 5, decimals = 0)
	private float epicentralDistance;

	@IntegerField(position = 77, digits = 3)
	private int azimuthAtSource;

	public String getStationName() {
		return stationName;
	}

	public InstrumentType getInstrumentType() {
		return instrumentType;
	}

	public char getComponent() {
		return component;
	}

	public char getEpicentral() {
		return epicentral;
	}

	public char getQualityIndicator() {
		return qualityIndicator;
	}

	public Phase getPhase() {
		return phase;
	}

	public int getWeightingIndicator() {
		return weightingIndicator;
	}

	public char getAutomaticPick() {
		return automaticPick;
	}

	public char getFirstMotion() {
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

	public int getDuration() {
		return duration;
	}

	public float getAplitude() {
		return aplitude;
	}

	public float getPeriodSeconds() {
		return periodSeconds;
	}

	public float getDirectionOfAproach() {
		return directionOfAproach;
	}

	public float getPhaseVelocity() {
		return phaseVelocity;
	}

	public float getAngleOfIncidence() {
		return angleOfIncidence;
	}

	public int getAzimuthResidual() {
		return azimuthResidual;
	}

	public float getTravelTimeResidual() {
		return travelTimeResidual;
	}

	public int getWeight() {
		return weight;
	}

	public float getEpicentralDistance() {
		return epicentralDistance;
	}

	public int getAzimuthAtSource() {
		return azimuthAtSource;
	}

}

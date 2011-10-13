package com.seismap.service.parser;

import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "F", after = { "I", "E", "F", "1" }, whitespaces = {
		@Whitespace(position = 37, length = 25),
		@Whitespace(position = 63, length = 4),
		@Whitespace(position = 70, length = 1),
		@Whitespace(position = 77, length = 1) })
public class TypeFEntry extends AbstractEntry {

	@FloatField(position = 1, digits = 10, decimals = 0)
	private float strike;

	@FloatField(position = 11, digits = 10, decimals = 0)
	private float dip;

	@FloatField(position = 21, digits = 10, decimals = 0)
	private float rake;

	@IntegerField(position = 31, digits = 6)
	private Integer badPolarities;

	@IntegerField(position = 62, digits = 1)
	private Integer undefinedNumber;

	@StringField(position = 67, length = 3)
	private String agencyCode;

	@StringField(position = 71, length = 6)
	private String methodSolution;

	@CharacterField(position = 78)
	private char qualitySolution;

	@CharacterField(position = 79)
	private char primeSolution;

	public float getStrike() {
		return strike;
	}

	public float getDip() {
		return dip;
	}

	public float getRake() {
		return rake;
	}

	public Integer getBadPolarities() {
		return badPolarities;
	}

	public Integer getUndefinedNumber() {
		return undefinedNumber;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public String getMethodSolution() {
		return methodSolution;
	}

	public char getQualitySolution() {
		return qualitySolution;
	}

	public char getPrimeSolution() {
		return primeSolution;
	}

}

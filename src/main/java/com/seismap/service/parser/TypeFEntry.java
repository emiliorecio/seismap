package com.seismap.service.parser;

import com.seismap.service.parser.annotation.CharacterField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.StringField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "F", after = { "4", "5", "6", "7" }, whitespaces = {
		@Whitespace(position = 37, length = 24),
		@Whitespace(position = 64, length = 6),
		@Whitespace(position = 77, length = 1) })
public class TypeFEntry {

	@FloatField(position = 1, digits = 10, decimals = 0)
	private float strike;

	@FloatField(position = 11, digits = 10, decimals = 0)
	private float dip;

	@FloatField(position = 21, digits = 10, decimals = 0)
	private float rake;

	@IntegerField(position = 31, digits = 6)
	private int badPolarities;

	@StringField(position = 61, length = 3)
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

	public int getBadPolarities() {
		return badPolarities;
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

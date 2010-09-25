package com.seismap.service.parser;

import com.seismap.service.parser.annotation.ConstantField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.ScientificNotationField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "E", after = { "4", "5", "6", "7", "F" }, whitespaces = {
		@Whitespace(position = 1, length = 1),
		@Whitespace(position = 31, length = 2) })
public class TypeEEntry extends AbstractEntry {

	@ConstantField(position = 2)
	static final String GAP_TEXT = "GAP=";

	@IntegerField(position = 6, digits = 3)
	private int gap;

	@FloatField(position = 15, digits = 6, decimals = 2)
	private int originTimeError;
	
	@FloatField(position = 25, digits = 6, decimals = 1)
	private int latitudeError;

	@FloatField(position = 33, digits = 6, decimals = 1)
	private int longitudeError;

	@FloatField(position = 39, digits = 5, decimals = 1)
	private int depthError;
	
	@ScientificNotationField(position = 44, digits = 12, exponentDigits = 4)
	private int xyCovariance;

	@ScientificNotationField(position = 56, digits = 12, exponentDigits = 4)
	private int xzCovariance;

	@ScientificNotationField(position = 68, digits = 14, exponentDigits = 4)
	private int yzCovariance;

	public int getGap() {
		return gap;
	}

	public int getOriginTimeError() {
		return originTimeError;
	}

	public int getLatitudeError() {
		return latitudeError;
	}

	public int getLongitudeError() {
		return longitudeError;
	}

	public int getDepthError() {
		return depthError;
	}

	public int getXyCovariance() {
		return xyCovariance;
	}

	public int getXzCovariance() {
		return xzCovariance;
	}

	public int getYzCovariance() {
		return yzCovariance;
	}

	
	
}

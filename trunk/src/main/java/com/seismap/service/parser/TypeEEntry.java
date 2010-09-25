package com.seismap.service.parser;

import com.seismap.service.parser.annotation.ConstantField;
import com.seismap.service.parser.annotation.Entry;
import com.seismap.service.parser.annotation.FloatField;
import com.seismap.service.parser.annotation.IntegerField;
import com.seismap.service.parser.annotation.ScientificNotationField;
import com.seismap.service.parser.annotation.Whitespace;

@Entry(id = "E", after = { "3" }, whitespaces = {
		@Whitespace(position = 1, length = 1),
		@Whitespace(position = 9, length = 6),
		@Whitespace(position = 21, length = 4),
		@Whitespace(position = 31, length = 2) })
public class TypeEEntry extends AbstractEntry {

	@ConstantField(position = 2)
	static final String GAP_TEXT = "GAP=";

	@IntegerField(position = 6, digits = 3)
	private int gap;

	@FloatField(position = 15, digits = 6, decimals = 2)
	private float originTimeError;

	@FloatField(position = 25, digits = 6, decimals = 1)
	private float latitudeError;

	@FloatField(position = 33, digits = 6, decimals = 1)
	private float longitudeError;

	@FloatField(position = 39, digits = 5, decimals = 1)
	private float depthError;

	@ScientificNotationField(position = 44, digits = 12, exponentDigits = 4)
	private float xyCovariance;

	@ScientificNotationField(position = 56, digits = 12, exponentDigits = 4)
	private float xzCovariance;

	@ScientificNotationField(position = 68, digits = 12, exponentDigits = 4)
	private float yzCovariance;

	public int getGap() {
		return gap;
	}

	public float getOriginTimeError() {
		return originTimeError;
	}

	public float getLatitudeError() {
		return latitudeError;
	}

	public float getLongitudeError() {
		return longitudeError;
	}

	public float getDepthError() {
		return depthError;
	}

	public float getXyCovariance() {
		return xyCovariance;
	}

	public float getXzCovariance() {
		return xzCovariance;
	}

	public float getYzCovariance() {
		return yzCovariance;
	}

}

package com.seismap.service.parser;

import com.seismap.service.parser.annotation.*;

@Entry(id = "F", after = { "1", "2", "3", "6", "I", "E", "F" }, whitespaces = {
		//@Whitespace(position = 37, length = 25),
		@Whitespace(position = 63, length = 1),
		@Whitespace(position = 66, length = 1),
		@Whitespace(position = 70, length = 1),
		@Whitespace(position = 79, length = 1) })
public class TypeFEntry extends AbstractEntry {

	@FloatField(position = 1, digits = 10, decimals = 0)
	private Float strike;

	@FloatField(position = 11, digits = 10, decimals = 0)
	private Float dip;

	@FloatField(position = 21, digits = 10, decimals = 0)
	private Float rake;

	@FloatField(position = 31, digits = 5, decimals = 1)
	private Float errorStrike;

	@FloatField(position = 36, digits = 5, decimals = 1)
	private Float errorDip;

	@FloatField(position = 41, digits = 5, decimals = 1)
	private Float errorRake;

	@FloatField(position = 46, digits = 5, decimals = 1)
	private Float fitError;

	@FloatField(position = 51, digits = 5, decimals = 1)
	private Float stationDistributionRatio;

	@FloatField(position = 56, digits = 5, decimals = 1)
	private Float amplitudeRatioFit;

	@IntegerField(position = 61, digits = 2)
	private Integer badPolarities;

	@IntegerField(position = 64, digits = 2)
	private Integer badAmplitude;

	@StringField(position = 67, length = 3)
	private String agencyCode;

	@StringField(position = 71, length = 7)
	private String programUsed;

	@StringField(position = 78, length = 1)
	private String qualitySolution;

	public Float getStrike() {
		return strike;
	}

	public Float getDip() {
		return dip;
	}

	public Float getRake() {
		return rake;
	}

	public Float getErrorStrike() {
		return errorStrike;
	}

	public Float getErrorDip() {
		return errorDip;
	}

	public Float getErrorRake() {
		return errorRake;
	}

	public Float getFitError() {
		return fitError;
	}

	public Float getStationDistributionRatio() {
		return stationDistributionRatio;
	}

	public Float getAmplitudeRatioFit() {
		return amplitudeRatioFit;
	}

	public Integer getBadPolarities() {
		return badPolarities;
	}

	public Integer getBadAmplitude() {
		return badAmplitude;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public String getProgramUsed() {
		return programUsed;
	}

	public String getQualitySolution() {
		return qualitySolution;
	}
}

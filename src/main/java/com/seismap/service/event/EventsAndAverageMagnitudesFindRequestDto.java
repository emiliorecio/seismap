package com.seismap.service.event;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RangeDto;

public class EventsAndAverageMagnitudesFindRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private RangeDto<Date> dateRange;

	@JsonProperty
	private RangeDto<Float> latitudeRange;

	@JsonProperty
	private RangeDto<Float> longitudeRange;

	@JsonProperty
	private RangeDto<Float> depthRange;

	@JsonProperty
	private MagnitudeType magnitudeType;

	@JsonProperty
	private RangeDto<Float> magnitudeRange;

	@JsonProperty
	private Boolean listUnmeasured;

	protected EventsAndAverageMagnitudesFindRequestDto() {

	}

	public EventsAndAverageMagnitudesFindRequestDto(RangeDto<Date> dateRange,
			RangeDto<Float> latitudeRange, RangeDto<Float> longitudeRange,
			RangeDto<Float> depthRange, MagnitudeType magnitudeType,
			RangeDto<Float> magnitudeRange, Boolean listUnmeasured) {
		super();
		this.dateRange = dateRange;
		this.latitudeRange = latitudeRange;
		this.longitudeRange = longitudeRange;
		this.depthRange = depthRange;
		this.magnitudeType = magnitudeType;
		this.magnitudeRange = magnitudeRange;
		this.listUnmeasured = listUnmeasured;
	}

	public RangeDto<Date> getDateRange() {
		return dateRange;
	}

	public void setDateRange(RangeDto<Date> dateRange) {
		this.dateRange = dateRange;
	}

	public RangeDto<Float> getLatitudeRange() {
		return latitudeRange;
	}

	public void setLatitudeRange(RangeDto<Float> latitudeRange) {
		this.latitudeRange = latitudeRange;
	}

	public RangeDto<Float> getLongitudeRange() {
		return longitudeRange;
	}

	public void setLongitudeRange(RangeDto<Float> longitudeRange) {
		this.longitudeRange = longitudeRange;
	}

	public RangeDto<Float> getDepthRange() {
		return depthRange;
	}

	public void setDepthRange(RangeDto<Float> depthRange) {
		this.depthRange = depthRange;
	}

	public MagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public void setMagnitudeType(MagnitudeType magnitudeType) {
		this.magnitudeType = magnitudeType;
	}

	public RangeDto<Float> getMagnitudeRange() {
		return magnitudeRange;
	}

	public void setMagnitudeRange(RangeDto<Float> magnitudeRange) {
		this.magnitudeRange = magnitudeRange;
	}

	public Boolean isListUnmeasured() {
		return listUnmeasured;
	}

	public void setListUnmeasured(Boolean listUnmeasured) {
		this.listUnmeasured = listUnmeasured;
	}

}

package com.seismap.service;

import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.model.entity.MagnitudeType;

public class EventsGetRequestDto {

	@JsonProperty
	private RangeDto<Date> dateRange;

	@JsonProperty
	private RangeDto<Float> latitudeRange;

	@JsonProperty
	private RangeDto<Float> longitudeRange;

	@JsonProperty
	private RangeDto<Float> depthRange;

	@JsonProperty
	private Map<MagnitudeType, RangeDto<Float>> magnitudeRanges;

	public EventsGetRequestDto() {

	}

	public EventsGetRequestDto(RangeDto<Date> dateRange,
			RangeDto<Float> latitudeRange, RangeDto<Float> longitudeRange,
			RangeDto<Float> depthRange,
			Map<MagnitudeType, RangeDto<Float>> magnitudeRanges) {
		this.dateRange = dateRange;
		this.latitudeRange = latitudeRange;
		this.longitudeRange = longitudeRange;
		this.depthRange = depthRange;
		this.magnitudeRanges = magnitudeRanges;
	}

	public RangeDto<Date> getDateRange() {
		return dateRange;
	}

	public RangeDto<Float> getLatitudeRange() {
		return latitudeRange;
	}

	public RangeDto<Float> getLongitudeRange() {
		return longitudeRange;
	}

	public RangeDto<Float> getDepthRange() {
		return depthRange;
	}

	public Map<MagnitudeType, RangeDto<Float>> getMagnitudeRanges() {
		return magnitudeRanges;
	}

}

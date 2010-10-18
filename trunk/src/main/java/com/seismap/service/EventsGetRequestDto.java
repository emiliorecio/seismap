package com.seismap.service;

import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.seismap.model.entity.MagnitudeType;

@XmlRootElement
public class EventsGetRequestDto {

	@XmlElement(required = true)
	private RangeDto<Date> dateRange;

	@XmlElement(required = true)
	private RangeDto<Float> latitudeRange;

	@XmlElement(required = true)
	private RangeDto<Float> longitudeRange;

	@XmlElement(required = true)
	private RangeDto<Float> depthRange;

	@XmlElement(required = true)
	private Map<MagnitudeType, RangeDto<Float>> magnitudeRanges;

	protected EventsGetRequestDto() {

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

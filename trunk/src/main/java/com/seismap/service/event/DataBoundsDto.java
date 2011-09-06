package com.seismap.service.event;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class DataBoundsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private Date minDate;

	@JsonProperty
	private Date maxDate;

	@JsonProperty
	private Float minDepth;

	@JsonProperty
	private Float maxDepth;

	@JsonProperty
	private Map<MagnitudeType, MagnitudeDataBoundsDto> magnitudeBounds;

	protected DataBoundsDto() {
	}

	public DataBoundsDto(Long id, Date minDate, Date maxDate, Float minDepth,
			Float maxDepth,
			Map<MagnitudeType, MagnitudeDataBoundsDto> magnitudeBounds) {
		super();
		this.id = id;
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
		this.magnitudeBounds = new HashMap<MagnitudeType, MagnitudeDataBoundsDto>(
				magnitudeBounds);
	}

	public Long getId() {
		return id;
	}

	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public Float getMinDepth() {
		return minDepth;
	}

	public Float getMaxDepth() {
		return maxDepth;
	}

	public Map<MagnitudeType, MagnitudeDataBoundsDto> getMagnitudeBounds() {
		return magnitudeBounds;
	}
}

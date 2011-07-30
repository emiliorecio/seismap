package com.seismap.service.event;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private Double latitude;

	@JsonProperty
	private Double longitude;

	@JsonProperty
	private Float depth;

	@JsonProperty
	private Date date;

	@JsonProperty
	private List<MagnitudeDto> magnitudes;

	public EventDto(Long id, Double latitude, Double longitude, Float depth,
			Date date, List<MagnitudeDto> magnitudes) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.date = date;
		this.magnitudes = magnitudes;
	}

	public Long getId() {
		return id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Float getDepth() {
		return depth;
	}

	public Date getDate() {
		return date;
	}

	public List<MagnitudeDto> getMagnitudes() {
		return magnitudes;
	}

}

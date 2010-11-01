package com.seismap.service;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;


public class EventDto {

	@JsonProperty
	private long id;

	@JsonProperty
	private float latitude;

	@JsonProperty
	private float longitude;

	@JsonProperty
	private float depth;

	@JsonProperty
	private Date date;

	@JsonProperty
	private MagnitudeDto[] magnitudes;

	public EventDto(long id, float latitude, float longitude, float depth,
			Date date, MagnitudeDto[] magnitudes) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.date = date;
		this.magnitudes = magnitudes;
	}

	public long getId() {
		return id;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getDepth() {
		return depth;
	}

	public Date getDate() {
		return date;
	}

	public MagnitudeDto[] getMagnitudes() {
		return magnitudes;
	}

}

package com.seismap.service.event;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;


public class EventAndAverageMagnitudeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long eventId;

	@JsonProperty
	private Double eventLatitude;

	@JsonProperty
	private Double eventLongitude;

	@JsonProperty
	private Float eventDepth;

	@JsonProperty
	private Date eventDate;

	@JsonProperty
	private MagnitudeType magnitudeType;

	@JsonProperty
	private Float magnitudeAverageValue;

	public EventAndAverageMagnitudeDto(Long eventId, Double eventLatitude,
			Double eventLongitude, Float eventDepth, Date eventDate,
			MagnitudeType magnitudeType, Float magnitudeAverageValue) {
		this.eventId = eventId;
		this.eventLatitude = eventLatitude;
		this.eventLongitude = eventLongitude;
		this.eventDepth = eventDepth;
		this.eventDate = eventDate;
		this.magnitudeType = magnitudeType;
		this.magnitudeAverageValue = magnitudeAverageValue;
	}

	public Long getEventId() {
		return eventId;
	}

	public Double getEventLatitude() {
		return eventLatitude;
	}

	public Double getEventLongitude() {
		return eventLongitude;
	}

	public Float getEventDepth() {
		return eventDepth;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public MagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public Float getMagnitudeAverageValue() {
		return magnitudeAverageValue;
	}

}

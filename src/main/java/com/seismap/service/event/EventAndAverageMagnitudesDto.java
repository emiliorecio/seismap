package com.seismap.service.event;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventAndAverageMagnitudesDto extends EventInfoDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Float averageMBMagnitude;

	@JsonProperty
	private Float averageMBLGMagnitude;

	@JsonProperty
	private Float averageMCMagnitude;

	@JsonProperty
	private Float averageMLMagnitude;

	@JsonProperty
	private Float averageMSMagnitude;

	@JsonProperty
	private Float averageMWMagnitude;

	protected EventAndAverageMagnitudesDto() {

	}

	public EventAndAverageMagnitudesDto(Long id, Double latitude,
			Double longitude, Float depth, Date date, String name,
			String notes, String reference, Float averageMLMagnitude,
			Float averageMBMagnitude, Float averageMSMagnitude,
			Float averageMWMagnitude, Float averageMBLGMagnitude,
			Float averageMCMagnitude) {
		super(id, latitude, longitude, depth, date, name, notes, reference);
		this.averageMLMagnitude = averageMLMagnitude;
		this.averageMBMagnitude = averageMBMagnitude;
		this.averageMSMagnitude = averageMSMagnitude;
		this.averageMWMagnitude = averageMWMagnitude;
		this.averageMBLGMagnitude = averageMBLGMagnitude;
		this.averageMCMagnitude = averageMCMagnitude;
	}

	public Float getAverageMBMagnitude() {
		return averageMBMagnitude;
	}

	public Float getAverageMBLGMagnitude() {
		return averageMBLGMagnitude;
	}

	public Float getAverageMCMagnitude() {
		return averageMCMagnitude;
	}

	public Float getAverageMLMagnitude() {
		return averageMLMagnitude;
	}

	public Float getAverageMSMagnitude() {
		return averageMSMagnitude;
	}

	public Float getAverageMWMagnitude() {
		return averageMWMagnitude;
	}

}

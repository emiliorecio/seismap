package com.seismap.service.event;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventInfoAndAverageMagnitudesDto extends EventInfoDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Float RANKMagnitude;

	@JsonProperty
	private Float MBMagnitude;

	@JsonProperty
	private Float MBLGMagnitude;

	@JsonProperty
	private Float MCMagnitude;

	@JsonProperty
	private Float MLMagnitude;

	@JsonProperty
	private Float MSMagnitude;

	@JsonProperty
	private Float MWMagnitude;

	protected EventInfoAndAverageMagnitudesDto() {
	}

	public EventInfoAndAverageMagnitudesDto(Long id, Double latitude,
			Double longitude, Float depth, Date date, String name,
			String notes, String reference, Float RANKMagnitude,
			Float MLMagnitude, Float MBMagnitude, Float MSMagnitude,
			Float MWMagnitude, Float MBLGMagnitude, Float MCMagnitude) {
		super(id, latitude, longitude, depth, date, name, notes, reference);
		this.RANKMagnitude = RANKMagnitude;
		this.MLMagnitude = MLMagnitude;
		this.MBMagnitude = MBMagnitude;
		this.MSMagnitude = MSMagnitude;
		this.MWMagnitude = MWMagnitude;
		this.MBLGMagnitude = MBLGMagnitude;
		this.MCMagnitude = MCMagnitude;
	}

	public Float getRANKMagnitude() {
		return RANKMagnitude;
	}

	public Float getMBMagnitude() {
		return MBMagnitude;
	}

	public Float getMBLGMagnitude() {
		return MBLGMagnitude;
	}

	public Float getMCMagnitude() {
		return MCMagnitude;
	}

	public Float getMLMagnitude() {
		return MLMagnitude;
	}

	public Float getMSMagnitude() {
		return MSMagnitude;
	}

	public Float getMWMagnitude() {
		return MWMagnitude;
	}

}

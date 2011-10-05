package com.seismap.service.event;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventAndAverageMagnitudesDto extends
		EventInfoAndAverageMagnitudesDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private List<MagnitudeDto> magnitudes;

	protected EventAndAverageMagnitudesDto() {
	}

	public EventAndAverageMagnitudesDto(Long id, Double latitude,
			Double longitude, Float depth, Date date, String name,
			String notes, String reference, Integer perceivedDistance,
			Integer damagedDistance, Float RANKMagnitude, Float MLMagnitude,
			Float MBMagnitude, Float MSMagnitude, Float MWMagnitude,
			Float MBLGMagnitude, Float MCMagnitude,
			List<MagnitudeDto> magnitudes) {
		super(id, latitude, longitude, depth, date, name, notes, reference,
				perceivedDistance, damagedDistance, RANKMagnitude, MLMagnitude,
				MBMagnitude, MSMagnitude, MWMagnitude, MBLGMagnitude,
				MCMagnitude);
		this.magnitudes = magnitudes;
	}

	public List<MagnitudeDto> getMagnitudes() {
		return magnitudes;
	}
}

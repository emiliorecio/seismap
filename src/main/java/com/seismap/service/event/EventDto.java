package com.seismap.service.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class EventDto extends EventInfoDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private List<MagnitudeDto> magnitudes;

	protected EventDto() {

	}

	public EventDto(Long id, Double latitude, Double longitude, Float depth,
			Date date, String name, String notes, String reference,
			Integer perceivedDistance, Integer damagedDistance,
			List<MagnitudeDto> magnitudes) {
		super(id, latitude, longitude, depth, date, name, notes, reference,
				perceivedDistance, damagedDistance);
		this.magnitudes = magnitudes;
	}

	public List<MagnitudeDto> getMagnitudes() {
		return magnitudes;
	}

}

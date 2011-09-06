package com.seismap.service.event;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventDto extends EventInfoDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private List<MagnitudeDto> magnitudes;

	protected EventDto() {

	}

	public EventDto(Long id, Double latitude, Double longitude, Float depth,
			Date date, List<MagnitudeDto> magnitudes) {
		super(id, latitude, longitude, depth, date);

		this.magnitudes = magnitudes;
	}

	public List<MagnitudeDto> getMagnitudes() {
		return magnitudes;
	}

}

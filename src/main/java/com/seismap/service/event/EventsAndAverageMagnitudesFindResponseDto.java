package com.seismap.service.event;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventsAndAverageMagnitudesFindResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private List<EventAndAverageMagnitudeDto> eventsAndAverageMagnitudes;

	protected EventsAndAverageMagnitudesFindResponseDto() {

	}

	public EventsAndAverageMagnitudesFindResponseDto(
			List<EventAndAverageMagnitudeDto> eventsAndAverageMagnitudes) {
		this.eventsAndAverageMagnitudes = eventsAndAverageMagnitudes;
	}

	public List<EventAndAverageMagnitudeDto> getEventsAndAverageMagnitudes() {
		return eventsAndAverageMagnitudes;
	}

}

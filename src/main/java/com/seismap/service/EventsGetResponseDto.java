package com.seismap.service;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventsGetResponseDto {

	@JsonProperty
	private EventDto[] events;

	public EventsGetResponseDto(EventDto[] events) {
		this.events = events;
	}

	public EventDto[] getEvents() {
		return events;
	}

}

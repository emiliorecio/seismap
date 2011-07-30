package com.seismap.service.event;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventGetResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private EventDto event;

	protected EventGetResponseDto() {

	}

	public EventGetResponseDto(EventDto event) {
		this.event = event;
	}

	public EventDto getEvent() {
		return event;
	}

}

package com.seismap.service.event;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventGetRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	private Long eventId;

	protected EventGetRequestDto() {

	}

	public EventGetRequestDto(Long eventId) {
		super();
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}
}

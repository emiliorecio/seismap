package com.seismap.service.event;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class GetEventRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long eventId;

	protected GetEventRequestDto() {

	}

	public GetEventRequestDto(Long eventId) {
		super();
		this.eventId = eventId;
	}

	public Long getEventId() {
		return eventId;
	}
}

package com.seismap.service.event;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class ModifyEventRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long eventId;

	@JsonProperty
	private ModifiableEventDataDto event;

	protected ModifyEventRequestDto() {
	}

	public ModifyEventRequestDto(Long eventId, ModifiableEventDataDto event) {
		this.eventId = eventId;
		this.event = event;
	}

	public Long getEventId() {
		return eventId;
	}

	public ModifiableEventDataDto getEvent() {
		return event;
	}

}

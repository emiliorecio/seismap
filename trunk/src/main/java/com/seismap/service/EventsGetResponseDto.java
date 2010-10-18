package com.seismap.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EventsGetResponseDto {

	@XmlElement(required = true)
	private EventDto[] events;

	public EventsGetResponseDto(EventDto[] events) {
		this.events = events;
	}

	public EventDto[] getEvents() {
		return events;
	}

}

package com.seismap.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.repository.EventRepository;
import com.seismap.service.EventService;
import com.seismap.service.EventsGetRequestDto;
import com.seismap.service.EventsGetResponseDto;

public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	protected EventServiceImpl() {
	}

	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Transactional
	public EventsGetResponseDto get(EventsGetRequestDto request) {
		return new EventsGetResponseDto(DtoMarshaler.unmarshall(eventRepository
				.get(DtoMarshaler.marshall(request.getDateRange()),
						DtoMarshaler.marshall(request.getLatitudeRange()),
						DtoMarshaler.marshall(request.getLongitudeRange()),
						DtoMarshaler.marshall(request.getDepthRange()),
						DtoMarshaler.marshallMagnitudeRange(request
								.getMagnitudeRanges()))));
	}

}

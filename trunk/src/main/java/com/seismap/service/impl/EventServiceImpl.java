package com.seismap.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.repository.EventAndAverageMagnitudeRepository;
import com.seismap.model.repository.EventRepository;
import com.seismap.service.event.EventGetRequestDto;
import com.seismap.service.event.EventGetResponseDto;
import com.seismap.service.event.EventService;
import com.seismap.service.event.EventsAndAverageMagnitudesFindRequestDto;
import com.seismap.service.event.EventsAndAverageMagnitudesFindResponseDto;

public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	private EventAndAverageMagnitudeRepository eventAndAverageMagnitudeRepository;

	protected EventServiceImpl() {
	}

	public EventServiceImpl(
			EventRepository eventRepository,
			EventAndAverageMagnitudeRepository eventAndAverageMagnitudeRepository) {
		this.eventRepository = eventRepository;
		this.eventAndAverageMagnitudeRepository = eventAndAverageMagnitudeRepository;
	}

	@Transactional
	public EventGetResponseDto get(EventGetRequestDto request) {
		return new EventGetResponseDto(
				DtoMarshaler.unmarshallEvent(eventRepository.get(request
						.getEventId())));
	}

	@Transactional
	public EventsAndAverageMagnitudesFindResponseDto find(
			EventsAndAverageMagnitudesFindRequestDto request) {
		return new EventsAndAverageMagnitudesFindResponseDto(
				DtoMarshaler
						.unmarshallEventsAndAverageMagnitudes(eventAndAverageMagnitudeRepository
								.find(request.getDateRange(), request
										.getLatitudeRange(), request
										.getLongitudeRange(), request
										.getDepthRange(), request
										.getMagnitudeType(), request
										.getMagnitudeRange(), request
										.isListUnmeasured().booleanValue())));
	}

}

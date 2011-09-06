package com.seismap.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.repository.DataBoundsRepository;
import com.seismap.model.repository.EventAndAverageMagnitudeSRepository;
import com.seismap.model.repository.EventRepository;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.event.EventService;
import com.seismap.service.event.FindEventsAndAverageMagnitudesRequestDto;
import com.seismap.service.event.FindEventsAndAverageMagnitudesResponseDto;
import com.seismap.service.event.GetDataBoundsRequestDto;
import com.seismap.service.event.GetDataBoundsResponseDto;
import com.seismap.service.event.GetEventRequestDto;
import com.seismap.service.event.GetEventResponseDto;

public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	private EventAndAverageMagnitudeSRepository eventAndAverageMagnitudeRepository;

	private DataBoundsRepository dataBoundsRepository;

	protected EventServiceImpl() {
	}

	public EventServiceImpl(
			EventRepository eventRepository,
			EventAndAverageMagnitudeSRepository eventAndAverageMagnitudeRepository,
			DataBoundsRepository dataBoundsRepository) {
		this.eventRepository = eventRepository;
		this.eventAndAverageMagnitudeRepository = eventAndAverageMagnitudeRepository;
		this.dataBoundsRepository = dataBoundsRepository;
	}

	@Transactional
	public GetEventResponseDto get(ActorCredentialsDto actorCredentials,
			GetEventRequestDto request) {
		return new GetEventResponseDto(
				DtoMarshaler.unmarshallEvent(eventRepository.get(request
						.getEventId())));
	}

	@Transactional
	public FindEventsAndAverageMagnitudesResponseDto find(
			ActorCredentialsDto actorCredentials,
			FindEventsAndAverageMagnitudesRequestDto request) {
		return new FindEventsAndAverageMagnitudesResponseDto(
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

	@Transactional
	public GetDataBoundsResponseDto getDataBounds(
			ActorCredentialsDto actorCredentials,
			GetDataBoundsRequestDto request) {
		return new GetDataBoundsResponseDto(DtoMarshaler
		.unmarshallDataBounds(dataBoundsRepository.get()));
	}

}
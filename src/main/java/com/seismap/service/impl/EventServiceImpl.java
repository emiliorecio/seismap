package com.seismap.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.Event;
import com.seismap.model.entity.EventAndAverageMagnitudes;
import com.seismap.model.entity.User;
import com.seismap.model.repository.DataBoundsRepository;
import com.seismap.model.repository.EventAndAverageMagnitudesRepository;
import com.seismap.model.repository.EventRepository;
import com.seismap.model.repository.MagnitudeLimitsRepository;
import com.seismap.model.repository.UserRepository;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;
import com.seismap.service.event.EventService;
import com.seismap.service.event.FindEventsAndAverageMagnitudesRequestDto;
import com.seismap.service.event.FindEventsAndAverageMagnitudesResponseDto;
import com.seismap.service.event.GetDataBoundsRequestDto;
import com.seismap.service.event.GetDataBoundsResponseDto;
import com.seismap.service.event.GetEventRequestDto;
import com.seismap.service.event.GetEventResponseDto;
import com.seismap.service.event.GetMagnitudeLimitsRequestDto;
import com.seismap.service.event.GetMagnitudeLimitsResponseDto;
import com.seismap.service.event.ModifiableEventDataDto;
import com.seismap.service.event.ModifyEventRequestDto;
import com.seismap.service.event.ModifyEventResponseDto;

public class EventServiceImpl extends AbstractServiceImpl implements
		EventService {

	private EventRepository eventRepository;

	private EventAndAverageMagnitudesRepository eventAndAverageMagnitudesRepository;

	private DataBoundsRepository dataBoundsRepository;

	private MagnitudeLimitsRepository magnitudeLimitsRepository;

	protected EventServiceImpl() {
	}

	public EventServiceImpl(
			UserRepository userRepository,
			EventRepository eventRepository,
			EventAndAverageMagnitudesRepository eventAndAverageMagnitudesRepository,
			DataBoundsRepository dataBoundsRepository,
			MagnitudeLimitsRepository magnitudeLimitsRepository) {
		super(userRepository);
		this.eventRepository = eventRepository;
		this.eventAndAverageMagnitudesRepository = eventAndAverageMagnitudesRepository;
		this.dataBoundsRepository = dataBoundsRepository;
		this.magnitudeLimitsRepository = magnitudeLimitsRepository;
	}

	@Transactional
	public GetEventResponseDto get(ActorCredentialsDto actorCredentials,
			GetEventRequestDto request) {
		try {
			validateUser(actorCredentials, Role.ANONYMOUS);
		} catch (UnauthorizedException e) {
			return new GetEventResponseDto(ExceptionCause.UNAUTHORIZED,
					e.getMessage());
		}
		Long eventId = request.getEventId();
		Event event = eventRepository.get(eventId);
		EventAndAverageMagnitudes eventAndAverageMagnitudes = eventAndAverageMagnitudesRepository
				.get(eventId);
		return new GetEventResponseDto(DtoMarshaler.unmarshallEvent(event,
				eventAndAverageMagnitudes));
	}

	@Transactional
	public FindEventsAndAverageMagnitudesResponseDto find(
			ActorCredentialsDto actorCredentials,
			FindEventsAndAverageMagnitudesRequestDto request) {
		try {
			validateUser(actorCredentials, Role.ANONYMOUS);
		} catch (UnauthorizedException e) {
			return new FindEventsAndAverageMagnitudesResponseDto(
					ExceptionCause.UNAUTHORIZED, e.getMessage());
		}
		return new FindEventsAndAverageMagnitudesResponseDto(
				DtoMarshaler
						.unmarshallEventsAndAverageMagnitudes(eventAndAverageMagnitudesRepository
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
		try {
			validateUser(actorCredentials, Role.ANONYMOUS);
		} catch (UnauthorizedException e) {
			return new GetDataBoundsResponseDto(ExceptionCause.UNAUTHORIZED,
					e.getMessage());
		}
		return new GetDataBoundsResponseDto(
				DtoMarshaler.unmarshallDataBounds(dataBoundsRepository.fetch()));
	}

	@Transactional
	public GetMagnitudeLimitsResponseDto getMagnitudeLimits(
			ActorCredentialsDto actorCredentials,
			GetMagnitudeLimitsRequestDto request) {
		try {
			validateUser(actorCredentials, Role.ANONYMOUS);
		} catch (UnauthorizedException e) {
			return new GetMagnitudeLimitsResponseDto(ExceptionCause.UNAUTHORIZED,
					e.getMessage());
		}
		return new GetMagnitudeLimitsResponseDto(
				DtoMarshaler
						.unmarshallMagnitudeLimits(magnitudeLimitsRepository
								.list()));
	}

	@Transactional
	public ModifyEventResponseDto modify(ActorCredentialsDto actorCredentials,
			ModifyEventRequestDto request) {
		User actor;
		try {
			actor = getValidatedUser(actorCredentials, Role.ADMIN);
		} catch (UnauthorizedException e) {
			return new ModifyEventResponseDto(ExceptionCause.UNAUTHORIZED,
					e.getMessage());
		}
		Long eventId = request.getEventId();
		Event event = eventRepository.get(eventId);
		if (event == null) {
			ModifyEventResponseDto exceptionResponse = new ModifyEventResponseDto(
					ExceptionCause.NO_EVENT_WITH_GIVEN_ID, "El evento "
							+ eventId + " no existe.");
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.EVENT_ID, eventId);
			return exceptionResponse;
		}
		if (!actor.isAdministrator()) {
			ModifyEventResponseDto exceptionResponse = new ModifyEventResponseDto(
					ExceptionCause.UNAUTHORIZED, "El usuario " + actor.getId()
							+ " no tiene permiso para realizar la operación.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.USER_ID,
					actor.getId());
			exceptionResponse.addExceptionParameter(ExceptionParameter.EVENT_ID,
					event.getId());
			return exceptionResponse;
		}
		ModifiableEventDataDto eventDataDto = request.getEvent();
		event.setName(eventDataDto.getName());
		event.setReference(eventDataDto.getReference());
		event.setNotes(eventDataDto.getNotes());
		event.setPerceivedDistance(eventDataDto.getPerceivedDistance());
		event.setDamagedDistance(eventDataDto.getDamagedDistance());

		EventAndAverageMagnitudes eventAndAverageMagnitudes = eventAndAverageMagnitudesRepository
				.get(eventId);
		return new ModifyEventResponseDto(DtoMarshaler.unmarshallEvent(event,
				eventAndAverageMagnitudes));
	}
}

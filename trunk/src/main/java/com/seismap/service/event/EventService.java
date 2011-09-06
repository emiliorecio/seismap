package com.seismap.service.event;

import com.seismap.service.common.ActorCredentialsDto;

public interface EventService {

	GetEventResponseDto get(
			ActorCredentialsDto actorCredentials,GetEventRequestDto request);

	FindEventsAndAverageMagnitudesResponseDto find(
			ActorCredentialsDto actorCredentials,
			FindEventsAndAverageMagnitudesRequestDto request);
	
	GetDataBoundsResponseDto getDataBounds(
			ActorCredentialsDto actorCredentials,
			GetDataBoundsRequestDto request);
}

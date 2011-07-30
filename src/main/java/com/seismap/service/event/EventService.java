package com.seismap.service.event;

public interface EventService {

	EventGetResponseDto get(EventGetRequestDto request);

	EventsAndAverageMagnitudesFindResponseDto find(
			EventsAndAverageMagnitudesFindRequestDto request);
}

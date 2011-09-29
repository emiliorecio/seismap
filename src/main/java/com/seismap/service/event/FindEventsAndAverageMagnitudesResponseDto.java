package com.seismap.service.event;

import java.util.List;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class FindEventsAndAverageMagnitudesResponseDto extends
		ResponseDto<List<EventInfoAndAverageMagnitudesDto>> {

	private static final long serialVersionUID = 1L;

	protected FindEventsAndAverageMagnitudesResponseDto() {

	}

	public FindEventsAndAverageMagnitudesResponseDto(
			List<EventInfoAndAverageMagnitudesDto> eventsAndAverageMagnitudes) {
		super(eventsAndAverageMagnitudes);
	}

	public FindEventsAndAverageMagnitudesResponseDto(ExceptionCause cause,
			String message) {
		super(cause, message);
	}
}

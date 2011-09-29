package com.seismap.service.event;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetEventResponseDto extends
		ResponseDto<EventAndAverageMagnitudesDto> {

	private static final long serialVersionUID = 1L;

	protected GetEventResponseDto() {

	}

	public GetEventResponseDto(EventAndAverageMagnitudesDto event) {
		super(event);
	}

	public GetEventResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

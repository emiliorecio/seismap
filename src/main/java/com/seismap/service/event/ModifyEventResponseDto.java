package com.seismap.service.event;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class ModifyEventResponseDto extends ResponseDto<EventAndAverageMagnitudesDto> {

	private static final long serialVersionUID = 1L;

	protected ModifyEventResponseDto() {
		super();
	}

	public ModifyEventResponseDto(EventAndAverageMagnitudesDto event) {
		super(event);
	}

	public ModifyEventResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

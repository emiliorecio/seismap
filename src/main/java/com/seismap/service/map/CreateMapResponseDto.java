package com.seismap.service.map;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class CreateMapResponseDto extends ResponseDto<MapDto> {

	private static final long serialVersionUID = 1L;

	protected CreateMapResponseDto() {
		super();
	}

	public CreateMapResponseDto(MapDto map) {
		super(map);
	}

	public CreateMapResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

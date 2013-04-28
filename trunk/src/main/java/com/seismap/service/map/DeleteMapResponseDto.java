package com.seismap.service.map;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class DeleteMapResponseDto extends ResponseDto<MapDto> {

	private static final long serialVersionUID = 1L;

	protected DeleteMapResponseDto() {
		super();
	}

	public DeleteMapResponseDto(MapDto map) {
		super(map);
	}

	public DeleteMapResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

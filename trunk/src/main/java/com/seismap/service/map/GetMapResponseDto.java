package com.seismap.service.map;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetMapResponseDto extends ResponseDto<MapDto> {

	private static final long serialVersionUID = 1L;

	protected GetMapResponseDto() {
		super();
	}

	public GetMapResponseDto(MapDto map) {
		super(map);
	}

	public GetMapResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

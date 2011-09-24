package com.seismap.service.map;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class ModifyMapResponseDto extends ResponseDto<MapDto> {

	private static final long serialVersionUID = 1L;

	protected ModifyMapResponseDto() {
		super();
	}

	public ModifyMapResponseDto(MapDto map) {
		super(map);
	}

	public ModifyMapResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

package com.seismap.service.map;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class RenameMapResponseDto extends ResponseDto<MapDto> {

	private static final long serialVersionUID = 1L;

	protected RenameMapResponseDto() {
		super();
	}

	public RenameMapResponseDto(MapDto map) {
		super(map);
	}

	public RenameMapResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

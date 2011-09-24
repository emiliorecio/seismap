package com.seismap.service.map;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetDefaultMapResponseDto extends ResponseDto<BasicMapDataDto> {

	private static final long serialVersionUID = 1L;

	protected GetDefaultMapResponseDto() {
		super();
	}

	public GetDefaultMapResponseDto(BasicMapDataDto map) {
		super(map);
	}

	public GetDefaultMapResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

package com.seismap.service.map;

import org.springframework.core.io.Resource;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetLegendResponseDto extends ResponseDto<Resource> {

	private static final long serialVersionUID = 1L;

	protected GetLegendResponseDto() {
		super();
	}

	public GetLegendResponseDto(Resource image) {
		super(image);
	}

	public GetLegendResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

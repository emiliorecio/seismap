package com.seismap.service.event;

import java.util.List;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetMagnitudeLimitsResponseDto extends
		ResponseDto<List<MagnitudeLimitsDto>> {

	private static final long serialVersionUID = 1L;

	protected GetMagnitudeLimitsResponseDto() {
		super();
	}

	public GetMagnitudeLimitsResponseDto(
			List<MagnitudeLimitsDto> magnitudeLimits) {
		super(magnitudeLimits);
	}

	public GetMagnitudeLimitsResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

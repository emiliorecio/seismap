package com.seismap.service.application;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetApplicationResponseDto extends ResponseDto<ApplicationDto> {

	private static final long serialVersionUID = 1L;

	protected GetApplicationResponseDto() {
		super();
	}

	public GetApplicationResponseDto(ApplicationDto application) {
		super(application);
	}

	public GetApplicationResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

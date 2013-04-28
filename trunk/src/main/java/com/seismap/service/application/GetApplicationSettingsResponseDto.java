package com.seismap.service.application;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetApplicationSettingsResponseDto extends
		ResponseDto<ApplicationSettingsDto> {

	private static final long serialVersionUID = 1L;

	protected GetApplicationSettingsResponseDto() {
		super();
	}

	public GetApplicationSettingsResponseDto(
			ApplicationSettingsDto applicationSettings) {
		super(applicationSettings);
	}

	public GetApplicationSettingsResponseDto(ExceptionCause cause,
			String message) {
		super(cause, message);
	}

}

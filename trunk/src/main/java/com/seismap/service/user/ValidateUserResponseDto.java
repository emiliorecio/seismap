package com.seismap.service.user;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class ValidateUserResponseDto extends ResponseDto<UserDto> {

	private static final long serialVersionUID = 1L;

	protected ValidateUserResponseDto() {
		super();
	}

	public ValidateUserResponseDto(UserDto user) {
		super(user);
	}

	public ValidateUserResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

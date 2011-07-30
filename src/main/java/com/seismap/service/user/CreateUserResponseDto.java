package com.seismap.service.user;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class CreateUserResponseDto extends ResponseDto<UserDto> {

	private static final long serialVersionUID = 1L;

	protected CreateUserResponseDto() {
		super();
	}

	public CreateUserResponseDto(UserDto user) {
		super(user);
	}

	public CreateUserResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

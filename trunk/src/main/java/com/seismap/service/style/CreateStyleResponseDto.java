package com.seismap.service.style;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class CreateStyleResponseDto extends ResponseDto<StyleDto> {

	private static final long serialVersionUID = 1L;

	protected CreateStyleResponseDto() {
		super();
	}

	public CreateStyleResponseDto(StyleDto style) {
		super(style);
	}

	public CreateStyleResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

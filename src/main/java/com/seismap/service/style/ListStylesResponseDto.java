package com.seismap.service.style;

import java.util.List;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class ListStylesResponseDto extends ResponseDto<List<StyleDto>> {

	private static final long serialVersionUID = 1L;

	protected ListStylesResponseDto() {
		super();
	}

	public ListStylesResponseDto(List<StyleDto> styles) {
		super(styles);
	}

	public ListStylesResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

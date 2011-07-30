package com.seismap.service.category;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class CreateCategoryResponseDto extends ResponseDto<CategoryDto> {

	private static final long serialVersionUID = 1L;

	protected CreateCategoryResponseDto() {
		super();
	}

	public CreateCategoryResponseDto(CategoryDto category) {
		super(category);
	}

	public CreateCategoryResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

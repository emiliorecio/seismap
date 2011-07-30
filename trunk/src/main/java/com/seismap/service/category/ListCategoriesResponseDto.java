package com.seismap.service.category;

import java.util.List;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class ListCategoriesResponseDto extends ResponseDto<List<CategoryDto>> {

	private static final long serialVersionUID = 1L;

	protected ListCategoriesResponseDto() {
		super();
	}

	public ListCategoriesResponseDto(List<CategoryDto> categories) {
		super(categories);
	}

	public ListCategoriesResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

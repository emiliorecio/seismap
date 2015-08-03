package com.seismap.service.category;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.seismap.service.common.RequestDto;

public class CreateCategoryRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String categoryName;

	protected CreateCategoryRequestDto() {
	}

	public CreateCategoryRequestDto(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

}

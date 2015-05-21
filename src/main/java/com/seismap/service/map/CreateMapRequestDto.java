package com.seismap.service.map;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.seismap.service.common.RequestDto;

public class CreateMapRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long userId;

	@JsonProperty
	private ModifiableMapDataDto map;

	protected CreateMapRequestDto() {
	}

	public CreateMapRequestDto(Long userId, ModifiableMapDataDto map) {
		this.userId = userId;
		this.map = map;
	}

	public Long getUserId() {
		return userId;
	}

	public ModifiableMapDataDto getMap() {
		return map;
	}
}

package com.seismap.service.map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class CreateMapRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long userId;

	@JsonProperty
	private BasicMapDataDto map;

	protected CreateMapRequestDto() {
	}

	public CreateMapRequestDto(Long userId, BasicMapDataDto map) {
		this.userId = userId;
		this.map = map;
	}

	public Long getUserId() {
		return userId;
	}

	public BasicMapDataDto getMap() {
		return map;
	}
}

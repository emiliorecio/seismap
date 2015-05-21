package com.seismap.service.map;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.seismap.service.common.RequestDto;

public class GetMapRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long mapId;

	protected GetMapRequestDto() {
	}

	public GetMapRequestDto(Long mapId) {
		this.mapId = mapId;
	}

	public Long getMapId() {
		return mapId;
	}

}

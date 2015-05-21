package com.seismap.service.map;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.seismap.service.common.RequestDto;

public class ModifyMapRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long mapId;

	@JsonProperty
	private ModifiableMapDataDto map;

	protected ModifyMapRequestDto() {
	}

	public ModifyMapRequestDto(Long mapId, ModifiableMapDataDto map) {
		this.mapId = mapId;
		this.map = map;
	}

	public Long getMapId() {
		return mapId;
	}

	public ModifiableMapDataDto getMap() {
		return map;
	}

}

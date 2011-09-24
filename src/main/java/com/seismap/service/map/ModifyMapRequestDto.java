package com.seismap.service.map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class ModifyMapRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long mapId;

	@JsonProperty
	private BasicMapDataDto map;

	protected ModifyMapRequestDto() {
	}

	public ModifyMapRequestDto(Long mapId, BasicMapDataDto map) {
		this.mapId = mapId;
		this.map = map;
	}

	public Long getMapId() {
		return mapId;
	}

	public BasicMapDataDto getMap() {
		return map;
	}

}

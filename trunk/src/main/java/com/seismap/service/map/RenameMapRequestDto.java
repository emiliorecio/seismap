package com.seismap.service.map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class RenameMapRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long mapId;

	@JsonProperty
	private String mapName;

	protected RenameMapRequestDto() {
	}

	public RenameMapRequestDto(Long mapId, String mapName) {
		this.mapId = mapId;
		this.mapName = mapName;
	}

	public Long getMapId() {
		return mapId;
	}

	public String getMapName() {
		return mapName;
	}

}

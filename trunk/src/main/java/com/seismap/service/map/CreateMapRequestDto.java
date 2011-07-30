package com.seismap.service.map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class CreateMapRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long userId;

	@JsonProperty
	private String mapName;

	protected CreateMapRequestDto() {
	}

	public CreateMapRequestDto(Long userId, String mapName) {
		this.userId = userId;
		this.mapName = mapName;
	}

	public Long getUserId() {
		return userId;
	}

	public String getMapName() {
		return mapName;
	}

}

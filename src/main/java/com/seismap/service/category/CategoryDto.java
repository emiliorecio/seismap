package com.seismap.service.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seismap.service.map.MapDto;

import java.io.Serializable;
import java.util.List;

public class CategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private String name;

	@JsonProperty
	private List<MapDto> maps;

	protected CategoryDto() {
	}

	public CategoryDto(Long id, String name, List<MapDto> maps) {
		this.id = id;
		this.name = name;
		this.maps = maps;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<MapDto> getMaps() {
		return maps;
	}

}

package com.seismap.service.category;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.map.MapDto;

public class CategoryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private String name;

	@JsonProperty
	private List<MapDto> maps = new LinkedList<MapDto>();

	@JsonProperty
	private Integer categoryIndex;

	protected CategoryDto() {
	}

	public CategoryDto(Long id, String name, Integer categoryIndex,
			List<MapDto> maps) {
		this.id = id;
		this.name = name;
		this.categoryIndex = categoryIndex;
		this.maps = maps;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getCategoryIndex() {
		return categoryIndex;
	}

	public List<MapDto> getMaps() {
		return maps;
	}

}

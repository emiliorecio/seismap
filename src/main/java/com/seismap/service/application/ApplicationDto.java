package com.seismap.service.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seismap.service.category.CategoryDto;
import com.seismap.service.style.StyleDto;

import java.io.Serializable;
import java.util.List;

public class ApplicationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private List<CategoryDto> categories;

	@JsonProperty
	private List<StyleDto> styles;

	@JsonProperty
	private ApplicationSettingsDto mapServiceSettings;

	protected ApplicationDto() {
	}

	public ApplicationDto(Long id, List<CategoryDto> categories,
			List<StyleDto> styles, ApplicationSettingsDto applicationSettings) {
		this.id = id;
		this.categories = categories;
		this.styles = styles;
		this.mapServiceSettings = applicationSettings;
	}

	public Long getId() {
		return id;
	}

	public List<CategoryDto> getCategories() {
		return categories;
	}

	public List<StyleDto> getStyles() {
		return styles;
	}

	public ApplicationSettingsDto getMapServiceSettings() {
		return mapServiceSettings;
	}

}

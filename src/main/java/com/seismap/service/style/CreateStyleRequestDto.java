package com.seismap.service.style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seismap.service.common.RequestDto;

import java.util.Map;

public class CreateStyleRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String styleSld;
	@JsonProperty
	private String styleName;
	@JsonProperty
	private Map<String, String> styleVariables;

	protected CreateStyleRequestDto() {
	}

	public CreateStyleRequestDto(String styleSld, String styleName,
			Map<String, String> styleVariables) {
		this.styleSld = styleSld;
		this.styleName = styleName;
		this.styleVariables = styleVariables;
	}

	public String getStyleSld() {
		return styleSld;
	}

	public String getStyleName() {
		return styleName;
	}

	public Map<String, String> getStyleVariables() {
		return styleVariables;
	}

}

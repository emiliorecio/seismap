package com.seismap.service.style;

import java.io.Serializable;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class StyleDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private String sld;

	@JsonProperty
	private String name;

	@JsonProperty
	private Map<String, String> variables;

	protected StyleDto() {
	}

	public StyleDto(Long id, String sld, String name,
			Map<String, String> variables) {
		this.id = id;
		this.sld = sld;
		this.name = name;
		this.variables = variables;
	}

	public Long getId() {
		return id;
	}

	public String getSld() {
		return sld;
	}

	public String getName() {
		return name;
	}

	public Map<String, String> getVariables() {
		return variables;
	}
}

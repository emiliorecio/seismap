package com.seismap.service.event;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class ModifiableEventDataDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String name;

	@JsonProperty
	private String notes;

	@JsonProperty
	private String reference;

	protected ModifiableEventDataDto() {

	}

	public ModifiableEventDataDto(String name, String notes, String reference) {
		this.name = name;
		this.notes = notes;
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public String getReference() {
		return reference;
	}

}

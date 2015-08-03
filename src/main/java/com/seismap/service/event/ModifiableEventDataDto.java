package com.seismap.service.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ModifiableEventDataDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String name;

	@JsonProperty
	private String notes;

	@JsonProperty
	private String reference;

	@JsonProperty
	private Integer perceivedDistance;

	@JsonProperty
	private Integer damagedDistance;

	protected ModifiableEventDataDto() {

	}

	public ModifiableEventDataDto(String name, String notes, String reference,
			Integer perceivedDistance, Integer damagedDistance) {
		this.name = name;
		this.notes = notes;
		this.reference = reference;
		this.perceivedDistance = perceivedDistance;
		this.damagedDistance = damagedDistance;
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

	public Integer getPerceivedDistance() {
		return perceivedDistance;
	}

	public Integer getDamagedDistance() {
		return damagedDistance;
	}

}

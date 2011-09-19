package com.seismap.service.event;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private Double latitude;

	@JsonProperty
	private Double longitude;

	@JsonProperty
	private Float depth;

	@JsonProperty
	private Date date;

	@JsonProperty
	private String name;

	@JsonProperty
	private String notes;

	@JsonProperty
	private String reference;

	protected EventInfoDto() {

	}

	public EventInfoDto(Long id, Double latitude, Double longitude,
			Float depth, Date date, String name, String notes, String reference) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.date = date;
		this.name = name;
		this.notes = notes;
		this.reference = reference;
	}

	public Long getId() {
		return id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Float getDepth() {
		return depth;
	}

	public Date getDate() {
		return date;
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

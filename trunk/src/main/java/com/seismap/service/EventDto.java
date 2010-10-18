package com.seismap.service;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EventDto {

	@XmlAttribute(required = true)
	private long id;

	@XmlAttribute(required = true)
	private float latitude;

	@XmlAttribute(required = true)
	private float longitude;

	@XmlAttribute(required = true)
	private float depth;

	@XmlAttribute(required = true)
	private Date date;

	@XmlElement(required = true)
	private MagnitudeDto[] magnitudes;

	public EventDto(long id, float latitude, float longitude, float depth,
			Date date, MagnitudeDto[] magnitudes) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.date = date;
		this.magnitudes = magnitudes;
	}

	public long getId() {
		return id;
	}

	public float getLatitude() {
		return latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public float getDepth() {
		return depth;
	}

	public Date getDate() {
		return date;
	}

	public MagnitudeDto[] getMagnitudes() {
		return magnitudes;
	}

}

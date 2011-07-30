package com.seismap.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.seismap.service.event.MagnitudeType;
import com.vividsolutions.jts.geom.Point;

@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EventAndAverageMagnitude implements
		Identifiable<EventIdAndMagnitudeTypePk> {

	@Id
	@Embedded
	private EventIdAndMagnitudeTypePk eventIdAndMagnitudeType;

	@Column(nullable = false)
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Point eventLocation;

	@Column(nullable = false)
	private float eventDepth;

	@Column(nullable = false)
	private Date eventDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "event_id", nullable = false)
	private List<Magnitude> eventMagnitudes;

	@Column(nullable = true)
	private Float magnitudeAverageValue;

	protected EventAndAverageMagnitude() {

	}

	public EventAndAverageMagnitude(Long eventId, Point eventLocation,
			float eventDepth, Date eventDate, List<Magnitude> eventMagnitudes,
			MagnitudeType magnitudeType, Float magnitudeAverageValue) {
		super();
		this.eventIdAndMagnitudeType = new EventIdAndMagnitudeTypePk(eventId,
				magnitudeType);
		this.eventLocation = eventLocation;
		this.eventDepth = eventDepth;
		this.eventDate = eventDate;
		this.eventMagnitudes = new ArrayList<Magnitude>(eventMagnitudes);
		this.magnitudeAverageValue = magnitudeAverageValue;
	}
	
	public EventIdAndMagnitudeTypePk getId() {
		return eventIdAndMagnitudeType;
	}

	public Long getEventId() {
		return eventIdAndMagnitudeType.getEventId();
	}

	public double getEventLatitude() {
		return eventLocation.getX();
	}

	public double getEventLongitude() {
		return eventLocation.getY();
	}

	public float getEventDepth() {
		return eventDepth;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public List<Magnitude> getEventMagnitudes() {
		return Collections.unmodifiableList(eventMagnitudes);
	}

	public MagnitudeType getMagnitudeType() {
		return eventIdAndMagnitudeType.getMagnitudeType();
	}

	public Float getMagnitudeAverageValue() {
		return magnitudeAverageValue;
	}
}

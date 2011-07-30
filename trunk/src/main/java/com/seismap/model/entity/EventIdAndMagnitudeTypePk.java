package com.seismap.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.seismap.service.event.MagnitudeType;

@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Embeddable
public class EventIdAndMagnitudeTypePk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private Long eventId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MagnitudeType magnitudeType;

	protected EventIdAndMagnitudeTypePk() {

	}

	public EventIdAndMagnitudeTypePk(Long eventId, MagnitudeType magnitudeType) {
		super();
		this.eventId = eventId;
		this.magnitudeType = magnitudeType;
	}

	public Long getEventId() {
		return eventId;
	}

	public MagnitudeType getMagnitudeType() {
		return magnitudeType;
	}
}

package com.seismap.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.vividsolutions.jts.geom.Point;

@Entity
public class Event extends EventInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "event_id", nullable = false)
	private List<Magnitude> magnitudes;

	protected Event() {

	}

	public Event(Point location, float depth, Date date, String name,
			String notes, String reference, Integer perceivedDistance,
			Integer damagedDistance, List<Magnitude> magnitudes) {
		super(location, depth, date, name, notes, reference, perceivedDistance,
				damagedDistance);
		this.magnitudes = new ArrayList<Magnitude>(magnitudes);
	}

	public Long getId() {
		return id;
	}

	public List<Magnitude> getMagnitudes() {
		return Collections.unmodifiableList(magnitudes);
	}

}

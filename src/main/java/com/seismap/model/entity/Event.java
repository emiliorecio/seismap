package com.seismap.model.entity;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "event")
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

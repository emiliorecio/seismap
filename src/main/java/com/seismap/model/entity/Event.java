package com.seismap.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@Entity
public class Event implements Identifiable<Long> {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Point location;

	@Column(nullable = false)
	private float depth;

	@Column(nullable = false)
	private Date date;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "event_id", nullable = false)
	private List<Magnitude> magnitudes;

	protected Event() {

	}

	public Event(Point location, float depth, Date date,
			List<Magnitude> magnitudes) {
		this.location = location;
		this.depth = depth;
		this.date = date;
		this.magnitudes = new ArrayList<Magnitude>(magnitudes);
	}

	public Long getId() {
		return id;
	}

	public Point getLocation() {
		return location;
	}

	public double getLatitude() {
		return location.getY();
	}

	public double getLongitude() {
		return location.getX();
	}

	public float getDepth() {
		return depth;
	}

	public Date getDate() {
		return date;
	}

	public List<Magnitude> getMagnitudes() {
		return Collections.unmodifiableList(magnitudes);
	}

}

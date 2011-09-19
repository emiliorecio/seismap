package com.seismap.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

@MappedSuperclass
public abstract class EventInfo implements Identifiable<Long> {

	@Column(nullable = false)
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Point location;

	@Column(nullable = false)
	private float depth;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = true)
	private String name;

	@Column(nullable = true)
	private String notes;

	@Column(nullable = true)
	private String reference;

	protected EventInfo() {

	}

	public EventInfo(Point location, float depth, Date date, String name,
			String notes, String reference) {
		this.location = location;
		this.depth = depth;
		this.date = date;
		this.name = name;
		this.notes = notes;
		this.reference = reference;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}

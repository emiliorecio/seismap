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

	protected EventInfo() {

	}

	public EventInfo(Point location, float depth, Date date) {
		this.location = location;
		this.depth = depth;
		this.date = date;
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
}

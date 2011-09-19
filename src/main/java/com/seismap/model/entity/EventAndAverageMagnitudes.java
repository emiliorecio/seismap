package com.seismap.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.vividsolutions.jts.geom.Point;

@Entity
public class EventAndAverageMagnitudes extends EventInfo {

	@Id
	@Column(nullable = false)
	private Long id;

	@Column(nullable = true)
	private Float averageRANKMagnitude;

	@Column(nullable = true)
	private Float averageMBMagnitude;

	@Column(nullable = true)
	private Float averageMBLGMagnitude;

	@Column(nullable = true)
	private Float averageMCMagnitude;

	@Column(nullable = true)
	private Float averageMLMagnitude;

	@Column(nullable = true)
	private Float averageMSMagnitude;

	@Column(nullable = true)
	private Float averageMWMagnitude;

	protected EventAndAverageMagnitudes() {

	}

	protected EventAndAverageMagnitudes(Long id, Point location, float depth,
			Date date, Float averageRANKMagnitude, Float averageMLMagnitude,
			Float averageMBMagnitude, Float averageMSMagnitude,
			Float averageMWMagnitude, Float averageMBLGMagnitude,
			Float averageMCMagnitude, String name, String notes, String reference) {
		super(location, depth, date, name, notes, reference);
		this.averageRANKMagnitude = averageRANKMagnitude;
		this.averageMLMagnitude = averageMLMagnitude;
		this.averageMBMagnitude = averageMBMagnitude;
		this.averageMSMagnitude = averageMSMagnitude;
		this.averageMWMagnitude = averageMWMagnitude;
		this.averageMBLGMagnitude = averageMBLGMagnitude;
		this.averageMCMagnitude = averageMCMagnitude;
	}

	public Long getId() {
		return id;
	}

	public Float getAverageRANKMagnitude() {
		return averageRANKMagnitude;
	}

	public Float getAverageMBMagnitude() {
		return averageMBMagnitude;
	}

	public Float getAverageMBLGMagnitude() {
		return averageMBLGMagnitude;
	}

	public Float getAverageMCMagnitude() {
		return averageMCMagnitude;
	}

	public Float getAverageMLMagnitude() {
		return averageMLMagnitude;
	}

	public Float getAverageMSMagnitude() {
		return averageMSMagnitude;
	}

	public Float getAverageMWMagnitude() {
		return averageMWMagnitude;
	}

}

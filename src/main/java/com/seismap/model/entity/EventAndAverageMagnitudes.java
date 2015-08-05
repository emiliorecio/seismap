package com.seismap.model.entity;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
//@Table(name = "eventandaveragemagnitudes")
public class EventAndAverageMagnitudes extends EventInfo {

	@Id
	@Column(nullable = false)
	private Long id;

	@Column(nullable = true)
	private Float RANKMagnitude;

	@Column(nullable = true)
	private Float MBMagnitude;

	@Column(nullable = true)
	private Float MBLGMagnitude;

	@Column(nullable = true)
	private Float MCMagnitude;

	@Column(nullable = true)
	private Float MLMagnitude;

	@Column(nullable = true)
	private Float MSMagnitude;

	@Column(nullable = true)
	private Float MWMagnitude;

	protected EventAndAverageMagnitudes() {

	}

	protected EventAndAverageMagnitudes(Long id, Point location, float depth,
			Date date, Float RANKMagnitude, Float MLMagnitude,
			Float MBMagnitude, Float MSMagnitude, Float MWMagnitude,
			Float MBLGMagnitude, Float MCMagnitude, String name, String notes,
			String reference, Integer perceivedDistance, Integer damagedDistance) {
		super(location, depth, date, name, notes, reference, perceivedDistance,
				damagedDistance);
		this.RANKMagnitude = RANKMagnitude;
		this.MLMagnitude = MLMagnitude;
		this.MBMagnitude = MBMagnitude;
		this.MSMagnitude = MSMagnitude;
		this.MWMagnitude = MWMagnitude;
		this.MBLGMagnitude = MBLGMagnitude;
		this.MCMagnitude = MCMagnitude;
	}

	public Long getId() {
		return id;
	}

	public Float getRANKMagnitude() {
		return RANKMagnitude;
	}

	public Float getMBMagnitude() {
		return MBMagnitude;
	}

	public Float getMBLGMagnitude() {
		return MBLGMagnitude;
	}

	public Float getMCMagnitude() {
		return MCMagnitude;
	}

	public Float getMLMagnitude() {
		return MLMagnitude;
	}

	public Float getMSMagnitude() {
		return MSMagnitude;
	}

	public Float getMWMagnitude() {
		return MWMagnitude;
	}

}

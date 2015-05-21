package com.seismap.model.entity;

import com.seismap.service.event.ExtendedMagnitudeType;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Entity
@Table(name = "databounds")
public class DataBounds implements Identifiable<Long> {

	@Id
	@Column(nullable = false)
	private Long id;

	@Column(nullable = true)
	private Date minDate;

	@Column(nullable = true)
	private Date maxDate;

	@Column(nullable = true)
	private Float minDepth;

	@Column(nullable = true)
	private Float maxDepth;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@MapKey(name = "magnitudeType")
	@JoinColumn(name = "dataBound_id")
	@Enumerated(EnumType.STRING)
	private java.util.Map<ExtendedMagnitudeType, MagnitudeDataBounds> magnitudeBounds = new LinkedHashMap<ExtendedMagnitudeType, MagnitudeDataBounds>();

	protected DataBounds() {
	}

	protected DataBounds(
			Long id,
			Date minDate,
			Date maxDate,
			Float minDepth,
			Float maxDepth,
			java.util.Map<ExtendedMagnitudeType, MagnitudeDataBounds> magnitudeBounds) {
		super();
		this.id = id;
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
		this.magnitudeBounds = new HashMap<ExtendedMagnitudeType, MagnitudeDataBounds>(
				magnitudeBounds);
	}

	public Long getId() {
		return id;
	}

	public Date getMinDate() {
		return minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public Float getMinDepth() {
		return minDepth;
	}

	public Float getMaxDepth() {
		return maxDepth;
	}

	public java.util.Map<ExtendedMagnitudeType, MagnitudeDataBounds> getMagnitudeBounds() {
		return Collections.unmodifiableMap(magnitudeBounds);
	}

}

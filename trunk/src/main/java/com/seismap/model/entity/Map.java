package com.seismap.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.seismap.service.event.MagnitudeType;
import com.seismap.service.map.AnimationType;
import com.seismap.service.map.DateUnits;
import com.vividsolutions.jts.geom.Point;

@Entity()
public class Map implements Identifiable<Long> {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(nullable = true, insertable = false, updatable = false)
	private Integer inCategoryIndex = null;

	@Column(nullable = true, insertable = false, updatable = false)
	private Integer inUserIndex = null;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private Point center;

	@Column(nullable = false)
	private int zoom;

	@Column(nullable = true)
	private Float minDateRelativeAmount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateUnits minDateRelativeUnits;

	@Column(nullable = true)
	private Date minDate;

	@Column(nullable = true)
	private Float maxDateRelativeAmount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateUnits maxDateRelativeUnits;

	@Column(nullable = true)
	private Date maxDate;

	@Column(nullable = true)
	private Float minDepth;

	@Column(nullable = true)
	private Float maxDepth;

	@Column(nullable = false)
	private MagnitudeType magnitudeType = MagnitudeType.ML;

	@Column(nullable = true)
	private Float minMagnitude;

	@Column(nullable = true)
	private Float maxMagnitude;

	@Column(nullable = false)
	private boolean listUnmeasured;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AnimationType animationType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private float animationStepKeep;

	@Column(nullable = false)
	private int animationSteps;

	@Column(nullable = false)
	private boolean reverseAnimation;

	protected Map() {
	}

	public Map(String name, String description, Point center, int zoom,
			Float minDateRelativeAmount, DateUnits minDateRelativeUnits,
			Date minDate, Float maxDateRelativeAmount,
			DateUnits maxDateRelativeUnits, Date maxDate, Float minDepth,
			Float maxDepth, MagnitudeType magnitudeType, Float minMagnitude,
			Float maxMagnitude, boolean listUnmeasured,
			AnimationType animationType, float animationStepKeep,
			int animationSteps, boolean reverseAnimation) {
		super();
		this.name = name;
		this.description = description;
		this.center = center;
		this.zoom = zoom;
		this.minDateRelativeAmount = minDateRelativeAmount;
		this.minDateRelativeUnits = minDateRelativeUnits;
		this.minDate = minDate;
		this.maxDateRelativeAmount = maxDateRelativeAmount;
		this.maxDateRelativeUnits = maxDateRelativeUnits;
		this.maxDate = maxDate;
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
		this.magnitudeType = magnitudeType;
		this.minMagnitude = minMagnitude;
		this.maxMagnitude = maxMagnitude;
		this.listUnmeasured = listUnmeasured;
		this.animationType = animationType;
		this.animationStepKeep = animationStepKeep;
		this.animationSteps = animationSteps;
		this.reverseAnimation = reverseAnimation;
	}

	public Long getId() {
		return id;
	}

	public Integer getInCateogryIndex() {
		return inCategoryIndex;
	}

	public Integer getInUserIndex() {
		return inUserIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public double getCenterLatitude() {
		return center.getY();
	}

	public double getCenterLongitude() {
		return center.getX();
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public Float getMinDateRelativeAmount() {
		return minDateRelativeAmount;
	}

	public void setMinDateRelativeAmount(Float minDateRelativeAmount) {
		this.minDateRelativeAmount = minDateRelativeAmount;
	}

	public DateUnits getMinDateRelativeUnits() {
		return minDateRelativeUnits;
	}

	public void setMinDateRelativeUnits(DateUnits minDateRelativeUnits) {
		this.minDateRelativeUnits = minDateRelativeUnits;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Float getMaxDateRelativeAmount() {
		return maxDateRelativeAmount;
	}

	public void setMaxDateRelativeAmount(Float maxDateRelativeAmount) {
		this.maxDateRelativeAmount = maxDateRelativeAmount;
	}

	public DateUnits getMaxDateRelativeUnits() {
		return maxDateRelativeUnits;
	}

	public void setMaxDateRelativeUnits(DateUnits maxDateRelativeUnits) {
		this.maxDateRelativeUnits = maxDateRelativeUnits;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Float getMinDepth() {
		return minDepth;
	}

	public void setMinDepth(Float minDepth) {
		this.minDepth = minDepth;
	}

	public Float getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(Float maxDepth) {
		this.maxDepth = maxDepth;
	}

	public MagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public void setMagnitudeType(MagnitudeType magnitudeType) {
		this.magnitudeType = magnitudeType;
	}

	public Float getMinMagnitude() {
		return minMagnitude;
	}

	public void setMinMagnitude(Float minMagnitude) {
		this.minMagnitude = minMagnitude;
	}

	public Float getMaxMagnitude() {
		return maxMagnitude;
	}

	public void setMaxMagnitude(Float maxMagnitude) {
		this.maxMagnitude = maxMagnitude;
	}

	public boolean isListUnmeasured() {
		return listUnmeasured;
	}

	public void setListUnmeasured(boolean listUnmeasured) {
		this.listUnmeasured = listUnmeasured;
	}

	public AnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(AnimationType animationType) {
		this.animationType = animationType;
	}

	public float getAnimationStepKeep() {
		return animationStepKeep;
	}

	public void setAnimationStepKeep(float animationStepKeep) {
		this.animationStepKeep = animationStepKeep;
	}

	public int getAnimationSteps() {
		return animationSteps;
	}

	public void setAnimationSteps(int animationSteps) {
		this.animationSteps = animationSteps;
	}

	public boolean isReverseAnimation() {
		return reverseAnimation;
	}

	public void setReverseAnimation(boolean reverseAnimation) {
		this.reverseAnimation = reverseAnimation;
	}

	void setInCategoryIndex(Integer inCategoryIndex) {
		this.inCategoryIndex = inCategoryIndex;
	}

	void setInUserIndex(Integer inUserIndex) {
		this.inUserIndex = inUserIndex;
	}
}

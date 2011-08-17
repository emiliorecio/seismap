package com.seismap.service.map;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.event.MagnitudeType;

public class MapDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@Column(nullable = false, insertable = false, updatable = false)
	private Integer mapIndex;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@JsonProperty
	private Double centerLatitude;

	@JsonProperty
	private Double centerLongitude;

	@Column(nullable = false)
	private Integer zoom;

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
	private MagnitudeType magnitudeType;

	@Column(nullable = true)
	private Float minMagnitude;

	@Column(nullable = true)
	private Float maxMagnitude;

	@Column(nullable = false)
	private Boolean listUnmeasured;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AnimationType animationType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private float animationStepKeep;

	@Column(nullable = false)
	private Integer animationSteps;

	@Column(nullable = false)
	private Boolean reverseAnimation;

	protected MapDto() {
	}

	public MapDto(Long id, String name, String description,
			Double centerLatitude, Double centerLongitude, Integer zoom,
			Float minDateRelativeAmount, DateUnits minDateRelativeUnits,
			Date minDate, Float maxDateRelativeAmount,
			DateUnits maxDateRelativeUnits, Date maxDate, Float minDepth,
			Float maxDepth, MagnitudeType magnitudeType, Float minMagnitude,
			Float maxMagnitude, Boolean listUnmeasured,
			AnimationType animationType, float animationStepKeep,
			Integer animationSteps, Boolean reverseAnimation) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.centerLatitude = centerLatitude;
		this.centerLongitude = centerLongitude;
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

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMapIndex() {
		return mapIndex;
	}

	public void setMapIndex(Integer mapIndex) {
		this.mapIndex = mapIndex;
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

	public Double getCenterLatitude() {
		return centerLatitude;
	}

	public void setCenterLatitude(Double centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

	public Double getCenterLongitude() {
		return centerLongitude;
	}

	public void setCenterLongitude(Double centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
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

	public Boolean isListUnmeasured() {
		return listUnmeasured;
	}

	public void setListUnmeasured(Boolean listUnmeasured) {
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

	public Integer getAnimationSteps() {
		return animationSteps;
	}

	public void setAnimationSteps(Integer animationSteps) {
		this.animationSteps = animationSteps;
	}

	public Boolean isReverseAnimation() {
		return reverseAnimation;
	}

	public void setReverseAnimation(Boolean reverseAnimation) {
		this.reverseAnimation = reverseAnimation;
	}

}

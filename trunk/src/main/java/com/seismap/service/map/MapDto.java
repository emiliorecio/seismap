package com.seismap.service.map;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.event.MagnitudeType;

public class MapDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private Integer mapIndex;

	@JsonProperty
	private String name;

	@JsonProperty
	private String description;

	@JsonProperty
	private Double centerLatitude;

	@JsonProperty
	private Double centerLongitude;

	@JsonProperty
	private Integer zoom;

	@JsonProperty
	private DateLimitType minDateType;

	@JsonProperty
	private Float minDateRelativeAmount;

	@JsonProperty
	private DateUnits minDateRelativeUnits;

	@JsonProperty
	private Date minDate;

	@JsonProperty
	private DateLimitType maxDateType;

	@JsonProperty
	private Float maxDateRelativeAmount;

	@JsonProperty
	private DateUnits maxDateRelativeUnits;

	@JsonProperty
	private Date maxDate;

	@JsonProperty
	private DepthLimitType minDepthType;

	@JsonProperty
	private Float minDepth;

	@JsonProperty
	private DepthLimitType maxDepthType;

	@JsonProperty
	private Float maxDepth;

	@JsonProperty
	private MagnitudeType magnitudeType;

	@JsonProperty
	private MagnitudeLimitType minMagnitudeType;

	@JsonProperty
	private Float minMagnitude;

	@JsonProperty
	private MagnitudeLimitType maxMagnitudeType;

	@JsonProperty
	private Float maxMagnitude;

	@JsonProperty
	private Boolean listUnmeasured;

	@JsonProperty
	private AnimationType animationType;

	@JsonProperty
	private float animationStepKeep;

	@JsonProperty
	private Integer animationSteps;

	@JsonProperty
	private Float animationStepDuration;

	@JsonProperty
	private Boolean reverseAnimation;

	protected MapDto() {
	}

	public MapDto(Long id, String name, String description,
			Double centerLatitude, Double centerLongitude, Integer zoom,
			DateLimitType minDateType, Float minDateRelativeAmount,
			DateUnits minDateRelativeUnits, Date minDate,
			DateLimitType maxDateType, Float maxDateRelativeAmount,
			DateUnits maxDateRelativeUnits, Date maxDate,
			DepthLimitType minDepthType, Float minDepth,
			DepthLimitType maxDepthType, Float maxDepth,
			MagnitudeType magnitudeType, MagnitudeLimitType minMagnitudeType,
			Float minMagnitude, MagnitudeLimitType maxMagnitudeType,
			Float maxMagnitude, Boolean listUnmeasured,
			AnimationType animationType, Float animationStepKeep,
			Integer animationSteps, Float animationStepDuration, Boolean reverseAnimation) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.centerLatitude = centerLatitude;
		this.centerLongitude = centerLongitude;
		this.zoom = zoom;
		this.minDateType = minDateType;
		this.minDateRelativeAmount = minDateRelativeAmount;
		this.minDateRelativeUnits = minDateRelativeUnits;
		this.minDate = minDate;
		this.maxDateType = maxDateType;
		this.maxDateRelativeAmount = maxDateRelativeAmount;
		this.maxDateRelativeUnits = maxDateRelativeUnits;
		this.maxDate = maxDate;
		this.minDepthType = minDepthType;
		this.minDepth = minDepth;
		this.maxDepthType = maxDepthType;
		this.maxDepth = maxDepth;
		this.magnitudeType = magnitudeType;
		this.minMagnitudeType = minMagnitudeType;
		this.minMagnitude = minMagnitude;
		this.maxMagnitudeType = maxMagnitudeType;
		this.maxMagnitude = maxMagnitude;
		this.listUnmeasured = listUnmeasured;
		this.animationType = animationType;
		this.animationStepKeep = animationStepKeep;
		this.animationSteps = animationSteps;
		this.animationStepDuration = animationStepDuration;
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

	public DateLimitType getMinDateType() {
		return minDateType;
	}

	public void setMinDateType(DateLimitType minDateType) {
		this.minDateType = minDateType;
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

	public DateLimitType getMaxDateType() {
		return maxDateType;
	}

	public void setMaxDateType(DateLimitType maxDateType) {
		this.maxDateType = maxDateType;
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

	public DepthLimitType getMinDepthType() {
		return minDepthType;
	}

	public void setMinDepthType(DepthLimitType minDepthType) {
		this.minDepthType = minDepthType;
	}

	public Float getMinDepth() {
		return minDepth;
	}

	public void setMinDepth(Float minDepth) {
		this.minDepth = minDepth;
	}

	public DepthLimitType getMaxDepthType() {
		return maxDepthType;
	}

	public void setMaxDepthType(DepthLimitType maxDepthType) {
		this.maxDepthType = maxDepthType;
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

	public MagnitudeLimitType getMinMagnitudeType() {
		return minMagnitudeType;
	}

	public void setMinMagnitudeType(MagnitudeLimitType minMagnitudeType) {
		this.minMagnitudeType = minMagnitudeType;
	}

	public Float getMinMagnitude() {
		return minMagnitude;
	}

	public void setMinMagnitude(Float minMagnitude) {
		this.minMagnitude = minMagnitude;
	}

	public MagnitudeLimitType getMaxMagnitudeType() {
		return maxMagnitudeType;
	}

	public void setMaxMagnitudeType(MagnitudeLimitType maxMagnitudeType) {
		this.maxMagnitudeType = maxMagnitudeType;
	}

	public Float getMaxMagnitude() {
		return maxMagnitude;
	}

	public void setMaxMagnitude(Float maxMagnitude) {
		this.maxMagnitude = maxMagnitude;
	}

	public void setListUnmeasured(Boolean listUnmeasured) {
		this.listUnmeasured = listUnmeasured;
	}
	
	public Boolean getListUnmeasured() {
		return listUnmeasured;
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

	public Float getAnimationStepDuration() {
		return animationStepDuration;
	}

	public void setAnimationStepDuration(Float animationStepDuration) {
		this.animationStepDuration = animationStepDuration;
	}

	public void setReverseAnimation(Boolean reverseAnimation) {
		this.reverseAnimation = reverseAnimation;
	}
	
	public Boolean getReverseAnimation() {
		return reverseAnimation;
	}

}

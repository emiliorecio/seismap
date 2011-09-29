package com.seismap.service.map;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.event.ExtendedMagnitudeType;

public class ModifiableMapDataDto implements Serializable {

	private static final long serialVersionUID = 1L;

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
	private ExtendedMagnitudeType magnitudeType;

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
	private Float animationStepKeep;

	@JsonProperty
	private Integer animationSteps;

	@JsonProperty
	private Float animationStepDuration;

	@JsonProperty
	private Boolean reverseAnimation;

	@JsonProperty
	private Long styleId;

	protected ModifiableMapDataDto() {
	}

	public ModifiableMapDataDto(String name, String description,
			Double centerLatitude, Double centerLongitude, Integer zoom,
			DateLimitType minDateType, Float minDateRelativeAmount,
			DateUnits minDateRelativeUnits, Date minDate,
			DateLimitType maxDateType, Float maxDateRelativeAmount,
			DateUnits maxDateRelativeUnits, Date maxDate,
			DepthLimitType minDepthType, Float minDepth,
			DepthLimitType maxDepthType, Float maxDepth,
			ExtendedMagnitudeType magnitudeType,
			MagnitudeLimitType minMagnitudeType, Float minMagnitude,
			MagnitudeLimitType maxMagnitudeType, Float maxMagnitude,
			Boolean listUnmeasured, AnimationType animationType,
			Float animationStepKeep, Integer animationSteps,
			Float animationStepDuration, Boolean reverseAnimation, Long styleId) {
		super();
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
		this.styleId = styleId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getCenterLatitude() {
		return centerLatitude;
	}

	public Double getCenterLongitude() {
		return centerLongitude;
	}

	public Integer getZoom() {
		return zoom;
	}

	public DateLimitType getMinDateType() {
		return minDateType;
	}

	public Float getMinDateRelativeAmount() {
		return minDateRelativeAmount;
	}

	public DateUnits getMinDateRelativeUnits() {
		return minDateRelativeUnits;
	}

	public Date getMinDate() {
		return minDate;
	}

	public DateLimitType getMaxDateType() {
		return maxDateType;
	}

	public Float getMaxDateRelativeAmount() {
		return maxDateRelativeAmount;
	}

	public DateUnits getMaxDateRelativeUnits() {
		return maxDateRelativeUnits;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public DepthLimitType getMinDepthType() {
		return minDepthType;
	}

	public Float getMinDepth() {
		return minDepth;
	}

	public DepthLimitType getMaxDepthType() {
		return maxDepthType;
	}

	public Float getMaxDepth() {
		return maxDepth;
	}

	public ExtendedMagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public MagnitudeLimitType getMinMagnitudeType() {
		return minMagnitudeType;
	}

	public Float getMinMagnitude() {
		return minMagnitude;
	}

	public MagnitudeLimitType getMaxMagnitudeType() {
		return maxMagnitudeType;
	}

	public Float getMaxMagnitude() {
		return maxMagnitude;
	}

	public Boolean getListUnmeasured() {
		return listUnmeasured;
	}

	public AnimationType getAnimationType() {
		return animationType;
	}

	public Float getAnimationStepKeep() {
		return animationStepKeep;
	}

	public Integer getAnimationSteps() {
		return animationSteps;
	}

	public Float getAnimationStepDuration() {
		return animationStepDuration;
	}

	public Boolean getReverseAnimation() {
		return reverseAnimation;
	}

	public Long getStyleId() {
		return styleId;
	}
}

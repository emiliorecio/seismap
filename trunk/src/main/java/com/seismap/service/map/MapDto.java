package com.seismap.service.map;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.event.ExtendedMagnitudeType;

public class MapDto extends ModifiableMapDataDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

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
			ExtendedMagnitudeType magnitudeType,
			MagnitudeLimitType minMagnitudeType, Float minMagnitude,
			MagnitudeLimitType maxMagnitudeType, Float maxMagnitude,
			Boolean listUnmeasured, AnimationType animationType,
			Float animationStepKeep, Integer animationSteps,
			Float animationStepDuration, Boolean reverseAnimation, Long styleId) {
		super(name, description, centerLatitude, centerLongitude, zoom,
				minDateType, minDateRelativeAmount, minDateRelativeUnits,
				minDate, maxDateType, maxDateRelativeAmount,
				maxDateRelativeUnits, maxDate, minDepthType, minDepth,
				maxDepthType, maxDepth, magnitudeType, minMagnitudeType,
				minMagnitude, maxMagnitudeType, maxMagnitude, listUnmeasured,
				animationType, animationStepKeep, animationSteps,
				animationStepDuration, reverseAnimation, styleId);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}

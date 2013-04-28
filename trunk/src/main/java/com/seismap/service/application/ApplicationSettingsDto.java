package com.seismap.service.application;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.model.entity.Style;
import com.seismap.service.event.ExtendedMagnitudeType;
import com.seismap.service.map.AnimationType;
import com.seismap.service.map.DateLimitType;
import com.seismap.service.map.DateUnits;
import com.seismap.service.map.DepthLimitType;
import com.seismap.service.map.MagnitudeLimitType;

public class ApplicationSettingsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private long settingsCacheExpiration;

	@JsonProperty
	private String layerServerUri;

	@JsonProperty
	private String googleMapsApiKey;

	@JsonProperty
	private int eventMapZoom;

	@JsonProperty
	private String layerName;

	@JsonProperty
	private String depthLayerName;

	@JsonProperty
	private String affectedDistanceStyleName;

	@JsonProperty
	private String defaultMapName;

	@JsonProperty
	private String defaultMapDescription;

	@JsonProperty
	private double defaultMapCenterLongitude;

	@JsonProperty
	private double defaultMapCenterLatitude;

	@JsonProperty
	private int defaultMapZoom;

	@JsonProperty
	private DateLimitType defaultMapMinDateType;

	@JsonProperty
	private float defaultMapMinDateRelativeAmount;

	@JsonProperty
	private DateUnits defaultMapMinDateRelativeUnits;

	@JsonProperty
	private Date defaultMapMinDate;

	@JsonProperty
	private DateLimitType defaultMapMaxDateType;

	@JsonProperty
	private float defaultMapMaxDateRelativeAmount;

	@JsonProperty
	private DateUnits defaultMapMaxDateRelativeUnits;

	@JsonProperty
	private Date defaultMapMaxDate;

	@JsonProperty
	private DepthLimitType defaultMapMinDepthType;

	@JsonProperty
	private Float defaultMapMinDepth;

	@JsonProperty
	private DepthLimitType defaultMapMaxDepthType;

	@JsonProperty
	private Float defaultMapMaxDepth;

	@JsonProperty
	private ExtendedMagnitudeType defaultMapMagnitudeType;

	@JsonProperty
	private MagnitudeLimitType defaultMapMinMagnitudeType;

	@JsonProperty
	private Float defaultMapMinMagnitude;

	@JsonProperty
	private MagnitudeLimitType defaultMapMaxMagnitudeType;

	@JsonProperty
	private Float defaultMapMaxMagnitude;

	@JsonProperty
	private boolean defaultMapListUnmeasured;

	@JsonProperty
	private AnimationType defaultMapAnimationType;

	@JsonProperty
	private float defaultMapAnimationStepKeep;

	@JsonProperty
	private int defaultMapAnimationSteps;

	@JsonProperty
	private float defaultMapAnimationStepDuration;

	@JsonProperty
	private boolean defaultMapReverseAnimation;

	@JsonProperty
	private Style defaultMapStyle;

	protected ApplicationSettingsDto() {
	}

	public ApplicationSettingsDto(long settingsCacheExpiration,
			String layerServerUri, String googleMapsApiKey, int eventMapZoom,
			String layerName, String depthLayerName,
			String affectedDistanceStyleName, String defaultMapName,
			String defaultMapDescription, double defaultMapCenterLongitude,
			double defaultMapCenterLatitude, int defaultMapZoom,
			DateLimitType defaultMapMinDateType,
			float defaultMapMinDateRelativeAmount,
			DateUnits defaultMapMinDateRelativeUnits, Date defaultMapMinDate,
			DateLimitType defaultMapMaxDateType,
			float defaultMapMaxDateRelativeAmount,
			DateUnits defaultMapMaxDateRelativeUnits, Date defaultMapMaxDate,
			DepthLimitType defaultMapMinDepthType, Float defaultMapMinDepth,
			DepthLimitType defaultMapMaxDepthType, Float defaultMapMaxDepth,
			ExtendedMagnitudeType defaultMapMagnitudeType,
			MagnitudeLimitType defaultMapMinMagnitudeType,
			Float defaultMapMinMagnitude,
			MagnitudeLimitType defaultMapMaxMagnitudeType,
			Float defaultMapMaxMagnitude, boolean defaultMapListUnmeasured,
			AnimationType defaultMapAnimationType,
			float defaultMapAnimationStepKeep, int defaultMapAnimationSteps,
			float defaultMapAnimationStepDuration,
			boolean defaultMapReverseAnimation, Style defaultMapStyle) {
		this.settingsCacheExpiration = settingsCacheExpiration;
		this.layerServerUri = layerServerUri;
		this.googleMapsApiKey = googleMapsApiKey;
		this.eventMapZoom = eventMapZoom;
		this.layerName = layerName;
		this.depthLayerName = depthLayerName;
		this.affectedDistanceStyleName = affectedDistanceStyleName;
		this.defaultMapName = defaultMapName;
		this.defaultMapDescription = defaultMapDescription;
		this.defaultMapCenterLongitude = defaultMapCenterLongitude;
		this.defaultMapCenterLatitude = defaultMapCenterLatitude;
		this.defaultMapZoom = defaultMapZoom;
		this.defaultMapMinDateType = defaultMapMinDateType;
		this.defaultMapMinDateRelativeAmount = defaultMapMinDateRelativeAmount;
		this.defaultMapMinDateRelativeUnits = defaultMapMinDateRelativeUnits;
		this.defaultMapMinDate = defaultMapMinDate;
		this.defaultMapMaxDateType = defaultMapMaxDateType;
		this.defaultMapMaxDateRelativeAmount = defaultMapMaxDateRelativeAmount;
		this.defaultMapMaxDateRelativeUnits = defaultMapMaxDateRelativeUnits;
		this.defaultMapMaxDate = defaultMapMaxDate;
		this.defaultMapMinDepthType = defaultMapMinDepthType;
		this.defaultMapMinDepth = defaultMapMinDepth;
		this.defaultMapMaxDepthType = defaultMapMaxDepthType;
		this.defaultMapMaxDepth = defaultMapMaxDepth;
		this.defaultMapMagnitudeType = defaultMapMagnitudeType;
		this.defaultMapMinMagnitudeType = defaultMapMinMagnitudeType;
		this.defaultMapMinMagnitude = defaultMapMinMagnitude;
		this.defaultMapMaxMagnitudeType = defaultMapMaxMagnitudeType;
		this.defaultMapMaxMagnitude = defaultMapMaxMagnitude;
		this.defaultMapListUnmeasured = defaultMapListUnmeasured;
		this.defaultMapAnimationType = defaultMapAnimationType;
		this.defaultMapAnimationStepKeep = defaultMapAnimationStepKeep;
		this.defaultMapAnimationSteps = defaultMapAnimationSteps;
		this.defaultMapAnimationStepDuration = defaultMapAnimationStepDuration;
		this.defaultMapReverseAnimation = defaultMapReverseAnimation;
		this.defaultMapStyle = defaultMapStyle;
	}

	public long getSettingsCacheExpiration() {
		return settingsCacheExpiration;
	}

	public String getLayerServerUri() {
		return layerServerUri;
	}

	public String getGoogleMapsApiKey() {
		return googleMapsApiKey;
	}

	public int getEventMapZoom() {
		return eventMapZoom;
	}

	public String getLayerName() {
		return layerName;
	}

	public String getDepthLayerName() {
		return depthLayerName;
	}

	public String getAffectedDistanceStyleName() {
		return affectedDistanceStyleName;
	}

	public String getDefaultMapName() {
		return defaultMapName;
	}

	public String getDefaultMapDescription() {
		return defaultMapDescription;
	}

	public double getDefaultMapCenterLongitude() {
		return defaultMapCenterLongitude;
	}

	public double getDefaultMapCenterLatitude() {
		return defaultMapCenterLatitude;
	}

	public int getDefaultMapZoom() {
		return defaultMapZoom;
	}

	public DateLimitType getDefaultMapMinDateType() {
		return defaultMapMinDateType;
	}

	public float getDefaultMapMinDateRelativeAmount() {
		return defaultMapMinDateRelativeAmount;
	}

	public DateUnits getDefaultMapMinDateRelativeUnits() {
		return defaultMapMinDateRelativeUnits;
	}

	public Date getDefaultMapMinDate() {
		return defaultMapMinDate;
	}

	public DateLimitType getDefaultMapMaxDateType() {
		return defaultMapMaxDateType;
	}

	public float getDefaultMapMaxDateRelativeAmount() {
		return defaultMapMaxDateRelativeAmount;
	}

	public DateUnits getDefaultMapMaxDateRelativeUnits() {
		return defaultMapMaxDateRelativeUnits;
	}

	public Date getDefaultMapMaxDate() {
		return defaultMapMaxDate;
	}

	public DepthLimitType getDefaultMapMinDepthType() {
		return defaultMapMinDepthType;
	}

	public Float getDefaultMapMinDepth() {
		return defaultMapMinDepth;
	}

	public DepthLimitType getDefaultMapMaxDepthType() {
		return defaultMapMaxDepthType;
	}

	public Float getDefaultMapMaxDepth() {
		return defaultMapMaxDepth;
	}

	public ExtendedMagnitudeType getDefaultMapMagnitudeType() {
		return defaultMapMagnitudeType;
	}

	public MagnitudeLimitType getDefaultMapMinMagnitudeType() {
		return defaultMapMinMagnitudeType;
	}

	public Float getDefaultMapMinMagnitude() {
		return defaultMapMinMagnitude;
	}

	public MagnitudeLimitType getDefaultMapMaxMagnitudeType() {
		return defaultMapMaxMagnitudeType;
	}

	public Float getDefaultMapMaxMagnitude() {
		return defaultMapMaxMagnitude;
	}

	public boolean isDefaultMapListUnmeasured() {
		return defaultMapListUnmeasured;
	}

	public AnimationType getDefaultMapAnimationType() {
		return defaultMapAnimationType;
	}

	public float getDefaultMapAnimationStepKeep() {
		return defaultMapAnimationStepKeep;
	}

	public int getDefaultMapAnimationSteps() {
		return defaultMapAnimationSteps;
	}

	public float getDefaultMapAnimationStepDuration() {
		return defaultMapAnimationStepDuration;
	}

	public boolean isDefaultMapReverseAnimation() {
		return defaultMapReverseAnimation;
	}

	public Style getDefaultMapStyle() {
		return defaultMapStyle;
	}

}

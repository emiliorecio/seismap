package com.seismap.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.seismap.service.event.ExtendedMagnitudeType;
import com.seismap.service.map.AnimationType;
import com.seismap.service.map.DateLimitType;
import com.seismap.service.map.DateUnits;
import com.seismap.service.map.DepthLimitType;
import com.seismap.service.map.MagnitudeLimitType;

@Embeddable
public class MapServiceSettings {

	@Column(nullable = false)
	private String layerServerUri;

	@Column(nullable = false)
	private String defaultMapDescription;

	@Column(nullable = false)
	private double defaultMapCenterLongitude;

	@Column(nullable = false)
	private double defaultMapCenterLatitude;

	@Column(nullable = false)
	private int defaultMapZoom;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateLimitType defaultMapMinDateType;

	@Column(nullable = false)
	private float defaultMapMinDateRelativeAmount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateUnits defaultMapMinDateRelativeUnits;

	@Column(nullable = true)
	private Date defaultMapMinDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateLimitType defaultMapMaxDateType;

	@Column(nullable = false)
	private float defaultMapMaxDateRelativeAmount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateUnits defaultMapMaxDateRelativeUnits;

	@Column(nullable = true)
	private Date defaultMapMaxDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DepthLimitType defaultMapMinDepthType;

	@Column(nullable = true)
	private Float defaultMapMinDepth;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DepthLimitType defaultMapMaxDepthType;

	@Column(nullable = true)
	private Float defaultMapMaxDepth;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ExtendedMagnitudeType defaultMapMagnitudeType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MagnitudeLimitType defaultMapMinMagnitudeType;

	@Column(nullable = true)
	private Float defaultMapMinMagnitude;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MagnitudeLimitType defaultMapMaxMagnitudeType;

	@Column(nullable = true)
	private Float defaultMapMaxMagnitude;

	@Column(nullable = false)
	private boolean defaultMapListUnmeasured;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AnimationType defaultMapAnimationType;

	@Column(nullable = false)
	private float defaultMapAnimationStepKeep;

	@Column(nullable = false)
	private int defaultMapAnimationSteps;

	@Column(nullable = false)
	private float defaultMapAnimationStepDuration;

	@Column(nullable = false)
	private boolean defaultMapReverseAnimation;

	@ManyToOne
	@JoinColumn(name = "defaultMapStyle_id", nullable = false)
	private Style defaultMapStyle;

	protected MapServiceSettings() {

	}

	protected MapServiceSettings(String layerServerUri,
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
		super();
		this.layerServerUri = layerServerUri;
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

	public String getLayerServerUri() {
		return layerServerUri;
	}

	public void setLayerServerUri(String layerServerUri) {
		this.layerServerUri = layerServerUri;
	}

	public String getDefaultMapDescription() {
		return defaultMapDescription;
	}

	public void setDefaultMapDescription(String defaultMapDescription) {
		this.defaultMapDescription = defaultMapDescription;
	}

	public double getDefaultMapCenterLongitude() {
		return defaultMapCenterLongitude;
	}

	public void setDefaultMapCenterLongitude(double defaultMapCenterLongitude) {
		this.defaultMapCenterLongitude = defaultMapCenterLongitude;
	}

	public double getDefaultMapCenterLatitude() {
		return defaultMapCenterLatitude;
	}

	public void setDefaultMapCenterLatitude(double defaultMapCenterLatitude) {
		this.defaultMapCenterLatitude = defaultMapCenterLatitude;
	}

	public int getDefaultMapZoom() {
		return defaultMapZoom;
	}

	public void setDefaultMapZoom(int defaultMapZoom) {
		this.defaultMapZoom = defaultMapZoom;
	}

	public DateLimitType getDefaultMapMinDateType() {
		return defaultMapMinDateType;
	}

	public void setDefaultMapMinDateType(DateLimitType defaultMapMinDateType) {
		this.defaultMapMinDateType = defaultMapMinDateType;
	}

	public float getDefaultMapMinDateRelativeAmount() {
		return defaultMapMinDateRelativeAmount;
	}

	public void setDefaultMapMinDateRelativeAmount(
			float defaultMapMinDateRelativeAmount) {
		this.defaultMapMinDateRelativeAmount = defaultMapMinDateRelativeAmount;
	}

	public DateUnits getDefaultMapMinDateRelativeUnits() {
		return defaultMapMinDateRelativeUnits;
	}

	public void setDefaultMapMinDateRelativeUnits(
			DateUnits defaultMapMinDateRelativeUnits) {
		this.defaultMapMinDateRelativeUnits = defaultMapMinDateRelativeUnits;
	}

	public Date getDefaultMapMinDate() {
		return defaultMapMinDate;
	}

	public void setDefaultMapMinDate(Date defaultMapMinDate) {
		this.defaultMapMinDate = defaultMapMinDate;
	}

	public DateLimitType getDefaultMapMaxDateType() {
		return defaultMapMaxDateType;
	}

	public void setDefaultMapMaxDateType(DateLimitType defaultMapMaxDateType) {
		this.defaultMapMaxDateType = defaultMapMaxDateType;
	}

	public float getDefaultMapMaxDateRelativeAmount() {
		return defaultMapMaxDateRelativeAmount;
	}

	public void setDefaultMapMaxDateRelativeAmount(
			float defaultMapMaxDateRelativeAmount) {
		this.defaultMapMaxDateRelativeAmount = defaultMapMaxDateRelativeAmount;
	}

	public DateUnits getDefaultMapMaxDateRelativeUnits() {
		return defaultMapMaxDateRelativeUnits;
	}

	public void setDefaultMapMaxDateRelativeUnits(
			DateUnits defaultMapMaxDateRelativeUnits) {
		this.defaultMapMaxDateRelativeUnits = defaultMapMaxDateRelativeUnits;
	}

	public Date getDefaultMapMaxDate() {
		return defaultMapMaxDate;
	}

	public void setDefaultMapMaxDate(Date defaultMapMaxDate) {
		this.defaultMapMaxDate = defaultMapMaxDate;
	}

	public DepthLimitType getDefaultMapMinDepthType() {
		return defaultMapMinDepthType;
	}

	public void setDefaultMapMinDepthType(DepthLimitType defaultMapMinDepthType) {
		this.defaultMapMinDepthType = defaultMapMinDepthType;
	}

	public Float getDefaultMapMinDepth() {
		return defaultMapMinDepth;
	}

	public void setDefaultMapMinDepth(Float defaultMapMinDepth) {
		this.defaultMapMinDepth = defaultMapMinDepth;
	}

	public DepthLimitType getDefaultMapMaxDepthType() {
		return defaultMapMaxDepthType;
	}

	public void setDefaultMapMaxDepthType(DepthLimitType defaultMapMaxDepthType) {
		this.defaultMapMaxDepthType = defaultMapMaxDepthType;
	}

	public Float getDefaultMapMaxDepth() {
		return defaultMapMaxDepth;
	}

	public void setDefaultMapMaxDepth(Float defaultMapMaxDepth) {
		this.defaultMapMaxDepth = defaultMapMaxDepth;
	}

	public ExtendedMagnitudeType getDefaultMapMagnitudeType() {
		return defaultMapMagnitudeType;
	}

	public void setDefaultMapMagnitudeType(
			ExtendedMagnitudeType defaultMapMagnitudeType) {
		this.defaultMapMagnitudeType = defaultMapMagnitudeType;
	}

	public MagnitudeLimitType getDefaultMapMinMagnitudeType() {
		return defaultMapMinMagnitudeType;
	}

	public void setDefaultMapMinMagnitudeType(
			MagnitudeLimitType defaultMapMinMagnitudeType) {
		this.defaultMapMinMagnitudeType = defaultMapMinMagnitudeType;
	}

	public Float getDefaultMapMinMagnitude() {
		return defaultMapMinMagnitude;
	}

	public void setDefaultMapMinMagnitude(Float defaultMapMinMagnitude) {
		this.defaultMapMinMagnitude = defaultMapMinMagnitude;
	}

	public MagnitudeLimitType getDefaultMapMaxMagnitudeType() {
		return defaultMapMaxMagnitudeType;
	}

	public void setDefaultMapMaxMagnitudeType(
			MagnitudeLimitType defaultMapMaxMagnitudeType) {
		this.defaultMapMaxMagnitudeType = defaultMapMaxMagnitudeType;
	}

	public Float getDefaultMapMaxMagnitude() {
		return defaultMapMaxMagnitude;
	}

	public void setDefaultMapMaxMagnitude(Float defaultMapMaxMagnitude) {
		this.defaultMapMaxMagnitude = defaultMapMaxMagnitude;
	}

	public boolean isDefaultMapListUnmeasured() {
		return defaultMapListUnmeasured;
	}

	public void setDefaultMapListUnmeasured(boolean defaultMapListUnmeasured) {
		this.defaultMapListUnmeasured = defaultMapListUnmeasured;
	}

	public AnimationType getDefaultMapAnimationType() {
		return defaultMapAnimationType;
	}

	public void setDefaultMapAnimationType(AnimationType defaultMapAnimationType) {
		this.defaultMapAnimationType = defaultMapAnimationType;
	}

	public float getDefaultMapAnimationStepKeep() {
		return defaultMapAnimationStepKeep;
	}

	public void setDefaultMapAnimationStepKeep(float defaultMapAnimationStepKeep) {
		this.defaultMapAnimationStepKeep = defaultMapAnimationStepKeep;
	}

	public int getDefaultMapAnimationSteps() {
		return defaultMapAnimationSteps;
	}

	public void setDefaultMapAnimationSteps(int defaultMapAnimationSteps) {
		this.defaultMapAnimationSteps = defaultMapAnimationSteps;
	}

	public float getDefaultMapAnimationStepDuration() {
		return defaultMapAnimationStepDuration;
	}

	public void setDefaultMapAnimationStepDuration(
			float defaultMapAnimationStepDuration) {
		this.defaultMapAnimationStepDuration = defaultMapAnimationStepDuration;
	}

	public boolean isDefaultMapReverseAnimation() {
		return defaultMapReverseAnimation;
	}

	public void setDefaultMapReverseAnimation(boolean defaultMapReverseAnimation) {
		this.defaultMapReverseAnimation = defaultMapReverseAnimation;
	}

	public Style getDefaultMapStyle() {
		return defaultMapStyle;
	}

	public void setDefaultMapStyle(Style defaultMapStyle) {
		this.defaultMapStyle = defaultMapStyle;
	}

}

package com.seismap.model.entity;

import com.seismap.service.event.ExtendedMagnitudeType;
import com.seismap.service.map.*;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Map implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne
	private User user;

	@Column(nullable = true, insertable = false, updatable = false)
	private Integer inCategoryIndex = null;

	@Column(nullable = true, insertable = false, updatable = false)
	private Integer inUserIndex = null;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	@Type(type = "org.hibernate.spatial.GeometryType")
	private Point center;

	@Column(nullable = false)
	private int zoom;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateLimitType minDateType;

	@Column(nullable = false)
	private float minDateRelativeAmount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateUnits minDateRelativeUnits;

	@Column(nullable = false)
	private Date minDate;

	@Column(nullable = false)
	private float maxDateRelativeAmount;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateUnits maxDateRelativeUnits;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DateLimitType maxDateType;

	@Column(nullable = false)
	private Date maxDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DepthLimitType minDepthType;

	@Column(nullable = true)
	private float minDepth;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private DepthLimitType maxDepthType;

	@Column(nullable = true)
	private float maxDepth;

	@Column(nullable = false)
	private ExtendedMagnitudeType magnitudeType;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MagnitudeLimitType minMagnitudeType;

	@Column(nullable = true)
	private float minMagnitude;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MagnitudeLimitType maxMagnitudeType;

	@Column(nullable = true)
	private float maxMagnitude;

	@Column(nullable = false)
	private boolean listUnmeasured;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AnimationType animationType;

	@Column(nullable = false)
	//@Enumerated(EnumType.STRING)
	private float animationStepKeep;

	@Column(nullable = false)
	private int animationSteps;

	@Column(nullable = false)
	private float animationStepDuration;

	@Column(nullable = false)
	private boolean reverseAnimation;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "style_id", nullable = false)
	private Style style;

	protected Map() {
	}

	public Map(User user, String name, String description, Point center,
			int zoom, DateLimitType minDateType, float minDateRelativeAmount,
			DateUnits minDateRelativeUnits, Date minDate,
			DateLimitType maxDateType, float maxDateRelativeAmount,
			DateUnits maxDateRelativeUnits, Date maxDate,
			DepthLimitType minDepthType, float minDepth,
			DepthLimitType maxDepthType, float maxDepth,
			ExtendedMagnitudeType magnitudeType,
			MagnitudeLimitType minMagnitudeType, float minMagnitude,
			MagnitudeLimitType maxMagnitudeType, float maxMagnitude,
			boolean listUnmeasured, AnimationType animationType,
			float animationStepKeep, int animationSteps,
			float animationStepDuration, boolean reverseAnimation, Style style) {
		super();
		this.user = user;
		this.name = name;
		this.description = description;
		this.center = center;
		this.zoom = zoom;
		this.minDateType = minDateType;
		this.minDateRelativeAmount = minDateRelativeAmount;
		this.minDateRelativeUnits = minDateRelativeUnits;
		this.maxDateType = maxDateType;
		this.minDate = minDate;
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
		this.style = style;
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
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

	public DateLimitType getMinDateType() {
		return minDateType;
	}

	public void setMinDateType(DateLimitType minDateType) {
		this.minDateType = minDateType;
	}

	public float getMinDateRelativeAmount() {
		return minDateRelativeAmount;
	}

	public void setMinDateRelativeAmount(float minDateRelativeAmount) {
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

	public DateLimitType getMaxDateType() {
		return maxDateType;
	}

	public void setMaxDateType(DateLimitType maxDateType) {
		this.maxDateType = maxDateType;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public float getMaxDateRelativeAmount() {
		return maxDateRelativeAmount;
	}

	public void setMaxDateRelativeAmount(float maxDateRelativeAmount) {
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

	public float getMinDepth() {
		return minDepth;
	}

	public void setMinDepth(float minDepth) {
		this.minDepth = minDepth;
	}

	public DepthLimitType getMaxDepthType() {
		return maxDepthType;
	}

	public void setMaxDepthType(DepthLimitType maxDepthType) {
		this.maxDepthType = maxDepthType;
	}

	public float getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(float maxDepth) {
		this.maxDepth = maxDepth;
	}

	public ExtendedMagnitudeType getMagnitudeType() {
		return magnitudeType;
	}

	public void setMagnitudeType(ExtendedMagnitudeType magnitudeType) {
		this.magnitudeType = magnitudeType;
	}

	public MagnitudeLimitType getMinMagnitudeType() {
		return minMagnitudeType;
	}

	public void setMinMagnitudeType(MagnitudeLimitType minMagnitudeType) {
		this.minMagnitudeType = minMagnitudeType;
	}

	public float getMinMagnitude() {
		return minMagnitude;
	}

	public void setMinMagnitude(float minMagnitude) {
		this.minMagnitude = minMagnitude;
	}

	public MagnitudeLimitType getMaxMagnitudeType() {
		return maxMagnitudeType;
	}

	public void setMaxMagnitudeType(MagnitudeLimitType maxMagnitudeType) {
		this.maxMagnitudeType = maxMagnitudeType;
	}

	public float getMaxMagnitude() {
		return maxMagnitude;
	}

	public void setMaxMagnitude(float maxMagnitude) {
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

	public void setAnimationStepDuration(float animationStepDuration) {
		this.animationStepDuration = animationStepDuration;
	}

	public float getAnimationStepDuration() {
		return animationStepDuration;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public Style getStyle() {
		return style;
	}

	public boolean isPublic() {
		return inCategoryIndex != null;
	}

}

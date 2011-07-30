package com.seismap.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.Map;
import com.seismap.model.entity.User;
import com.seismap.model.repository.MapRepository;
import com.seismap.model.repository.UserRepository;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;
import com.seismap.service.event.MagnitudeType;
import com.seismap.service.map.AnimationType;
import com.seismap.service.map.CreateMapRequestDto;
import com.seismap.service.map.CreateMapResponseDto;
import com.seismap.service.map.DateUnits;
import com.seismap.service.map.GetMapRequestDto;
import com.seismap.service.map.GetMapResponseDto;
import com.seismap.service.map.ListUserMapsRequestDto;
import com.seismap.service.map.ListUserMapsResponseDto;
import com.seismap.service.map.MapDto;
import com.seismap.service.map.MapService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class MapServiceImpl implements MapService {

	private MapRepository mapRepository;
	private UserRepository userRepository;
	private GeometryFactory geometryFactory;

	private String defaultMapDescription = "";
	private double defaultMapCenterLongitude = -68.525278d;
	private double defaultMapCenterLatitude = -31.537222d;
	private int defaultMapZoom = 5;
	private Integer defaultMapMinDateRelativeAmount = null;
	private DateUnits defaultMapMinDateRelativeUnits = DateUnits.DAY;
	private Date defaultMapMinDate = null;
	private Integer defaultMapMaxDateRelativeAmount = null;
	private DateUnits defaultMapMaxDateRelativeUnits = DateUnits.DAY;
	private Date defaultMapMaxDate = null;
	private Float defaultMapMinDepth = null;
	private Float defaultMapMaxDepth = null;
	private MagnitudeType defaultMapMagnitudeType = MagnitudeType.MB;
	private Float defaultMapMonMagnitude = null;
	private Float defaultMapMaxMagnitude = null;
	private boolean defaultMapListUnmeasured = true;
	private AnimationType defaultMapAnimationType = AnimationType.NONE;
	private float defaultMapAnimationStepKeep = 0;
	private int defaultMapAnimationSteps = 10;
	private boolean defaultMapReverseAnimation = false;

	protected MapServiceImpl() {
	}

	public MapServiceImpl(MapRepository mapRepository,
			UserRepository userRepository, GeometryFactory geometryFactory) {
		this.mapRepository = mapRepository;
		this.geometryFactory = geometryFactory;
		this.userRepository = userRepository;
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

	public void setDefaultMapCenterX(double defaultMapCenterLongitude) {
		this.defaultMapCenterLongitude = defaultMapCenterLongitude;
	}

	public double getDefaultMapCenterLatitude() {
		return defaultMapCenterLatitude;
	}

	public void setDefaultMapCenterY(double defaultMapCenterLatitude) {
		this.defaultMapCenterLatitude = defaultMapCenterLatitude;
	}

	public int getDefaultMapZoom() {
		return defaultMapZoom;
	}

	public void setDefaultMapZoom(int defaultMapZoom) {
		this.defaultMapZoom = defaultMapZoom;
	}

	public Integer getDefaultMapMinDateRelativeAmount() {
		return defaultMapMinDateRelativeAmount;
	}

	public void setDefaultMapMinDateRelativeAmount(
			Integer defaultMapMinDateRelativeAmount) {
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

	public Integer getDefaultMapMaxDateRelativeAmount() {
		return defaultMapMaxDateRelativeAmount;
	}

	public void setDefaultMapMaxDateRelativeAmount(
			Integer defaultMapMaxDateRelativeAmount) {
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

	public Float getDefaultMapMinDepth() {
		return defaultMapMinDepth;
	}

	public void setDefaultMapMinDepth(Float defaultMapMinDepth) {
		this.defaultMapMinDepth = defaultMapMinDepth;
	}

	public Float getDefaultMapMaxDepth() {
		return defaultMapMaxDepth;
	}

	public void setDefaultMapMaxDepth(Float defaultMapMaxDepth) {
		this.defaultMapMaxDepth = defaultMapMaxDepth;
	}

	public MagnitudeType getDefaultMapMagnitudeType() {
		return defaultMapMagnitudeType;
	}

	public void setDefaultMapMagnitudeType(MagnitudeType defaultMapMagnitudeType) {
		this.defaultMapMagnitudeType = defaultMapMagnitudeType;
	}

	public Float getDefaultMapMonMagnitude() {
		return defaultMapMonMagnitude;
	}

	public void setDefaultMapMonMagnitude(Float defaultMapMonMagnitude) {
		this.defaultMapMonMagnitude = defaultMapMonMagnitude;
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

	public boolean isDefaultMapReverseAnimation() {
		return defaultMapReverseAnimation;
	}

	public void setDefaultMapReverseAnimation(boolean defaultMapReverseAnimation) {
		this.defaultMapReverseAnimation = defaultMapReverseAnimation;
	}

	@Transactional
	public CreateMapResponseDto create(ActorCredentialsDto actorCredentials,
			CreateMapRequestDto request) {
		String name = request.getMapName();
		Long userId = request.getUserId();
		User user = userRepository.get(userId);
		if (user == null) {
			CreateMapResponseDto exceptionResponse = new CreateMapResponseDto(
					ExceptionCause.DUPLICATE_MAP_NAME, "El usuario " + userId
							+ " no existe.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.USER_ID,
					userId);
			return exceptionResponse;
		}
		Map existingMap = mapRepository.getByUserAndName(user, name);
		if (existingMap != null) {
			String userEmail = user.getEmail();
			CreateMapResponseDto exceptionResponse = new CreateMapResponseDto(
					ExceptionCause.DUPLICATE_MAP_NAME, "La usuario '"
							+ userEmail + "' ya contiene un mapa llamado '"
							+ name + "'.");
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.USER_EMAIL, userEmail);
			exceptionResponse.addExceptionParameter(ExceptionParameter.USER_ID,
					userId);
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.MAP_NAME, name);
			return exceptionResponse;
		}
		String description = defaultMapDescription;
		Point center = geometryFactory.createPoint(new Coordinate(
				defaultMapCenterLongitude, defaultMapCenterLatitude));
		int zoom = defaultMapZoom;
		Integer minDateRelativeAmount = defaultMapMinDateRelativeAmount;
		DateUnits minDateRelativeUnits = defaultMapMinDateRelativeUnits;
		Date minDate = defaultMapMinDate;
		Integer maxDateRelativeAmount = defaultMapMaxDateRelativeAmount;
		DateUnits maxDateRelativeUnits = defaultMapMaxDateRelativeUnits;
		Date maxDate = defaultMapMaxDate;
		Float minDepth = defaultMapMinDepth;
		Float maxDepth = defaultMapMaxDepth;
		MagnitudeType magnitudeType = defaultMapMagnitudeType;
		Float minMagnitude = defaultMapMonMagnitude;
		Float maxMagnitude = defaultMapMaxMagnitude;
		boolean listUnmeasured = defaultMapListUnmeasured;
		AnimationType animationType = defaultMapAnimationType;
		float animationStepKeep = defaultMapAnimationStepKeep;
		int animationSteps = defaultMapAnimationSteps;
		boolean reverseAnimation = defaultMapReverseAnimation;
		Map map = new Map(name, description, center, zoom,
				minDateRelativeAmount, minDateRelativeUnits, minDate,
				maxDateRelativeAmount, maxDateRelativeUnits, maxDate, minDepth,
				maxDepth, magnitudeType, minMagnitude, maxMagnitude,
				listUnmeasured, animationType, animationStepKeep,
				animationSteps, reverseAnimation);
		user.add(map);
		mapRepository.put(map);
		MapDto mapDto = DtoMarshaler.unmarshallMap(map);
		CreateMapResponseDto response = new CreateMapResponseDto(mapDto);
		return response;
	}

	@Transactional
	public GetMapResponseDto get(ActorCredentialsDto actorCredentials,
			GetMapRequestDto request) {
		Long id = request.getMapId();
		Map map = mapRepository.get(id);
		if (map == null) {
			GetMapResponseDto exceptionResponse = new GetMapResponseDto(
					ExceptionCause.NO_MAP_WITH_GIVEN_ID, "El mapa " + id
							+ " no existe.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.MAP_ID,
					id);
			return exceptionResponse;
		}
		MapDto mapDto = DtoMarshaler.unmarshallMap(map);
		GetMapResponseDto response = new GetMapResponseDto(mapDto);
		return response;
	}

	@Transactional
	public ListUserMapsResponseDto listByUser(
			ActorCredentialsDto actorCredentials, ListUserMapsRequestDto request) {
		Long userId = actorCredentials.getUserId();
		User user = userRepository.get(userId);
		if (user == null) {
			ListUserMapsResponseDto exceptionResponse = new ListUserMapsResponseDto(
					ExceptionCause.NO_MAP_WITH_GIVEN_ID, "El usuario " + userId
							+ " no existe.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.USER_ID,
					userId);
			return exceptionResponse;
		}
		return new ListUserMapsResponseDto(DtoMarshaler.unmarshallMaps(user
				.getMaps()));
	}

}

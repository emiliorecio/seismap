package com.seismap.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.seismap.service.map.DateLimitType;
import com.seismap.service.map.DateUnits;
import com.seismap.service.map.DepthLimitType;
import com.seismap.service.map.GetLayerServerUriRequestDto;
import com.seismap.service.map.GetLayerServerUriResponseDto;
import com.seismap.service.map.GetMapRequestDto;
import com.seismap.service.map.GetMapResponseDto;
import com.seismap.service.map.ListUserMapsRequestDto;
import com.seismap.service.map.ListUserMapsResponseDto;
import com.seismap.service.map.MagnitudeLimitType;
import com.seismap.service.map.MapDto;
import com.seismap.service.map.MapService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class MapServiceImpl implements MapService {

	private MapRepository mapRepository;
	private UserRepository userRepository;
	private GeometryFactory geometryFactory;

	private String layerServerUri;

	private String defaultMapDescription = "";
	private double defaultMapCenterLongitude = -68.525278d;
	private double defaultMapCenterLatitude = -31.537222d;
	private int defaultMapZoom = 5;
	private DateLimitType defaultMapMinDateType = DateLimitType.NONE;
	private float defaultMapMinDateRelativeAmount = 1f;
	private DateUnits defaultMapMinDateRelativeUnits = DateUnits.DAY;
	private Date defaultMapMinDate = null;
	private DateLimitType defaultMapMaxDateType = DateLimitType.NONE;
	private float defaultMapMaxDateRelativeAmount = 0f;
	private DateUnits defaultMapMaxDateRelativeUnits = DateUnits.DAY;
	private Date defaultMapMaxDate = null;
	private DepthLimitType defaultMapMinDepthType = DepthLimitType.NONE;
	private float defaultMapMinDepth = 0f;
	private DepthLimitType defaultMapMaxDepthType = DepthLimitType.NONE;
	private float defaultMapMaxDepth = 1000f;
	private MagnitudeType defaultMapMagnitudeType = MagnitudeType.MB;
	private MagnitudeLimitType defaultMapMinMagnitudeType = MagnitudeLimitType.NONE;
	private float defaultMapMinMagnitude = 0f;
	private MagnitudeLimitType defaultMapMaxMagnitudeType = MagnitudeLimitType.NONE;
	private float defaultMapMaxMagnitude = 100f;
	private boolean defaultMapListUnmeasured = true;
	private AnimationType defaultMapAnimationType = AnimationType.NONE;
	private float defaultMapAnimationStepKeep = 0;
	private int defaultMapAnimationSteps = 10;
	private float defaultMapAnimationStepDuration = 5;
	private boolean defaultMapReverseAnimation = false;

	protected MapServiceImpl() {
	}

	public MapServiceImpl(MapRepository mapRepository,
			UserRepository userRepository, GeometryFactory geometryFactory) {
		this.mapRepository = mapRepository;
		this.geometryFactory = geometryFactory;
		this.userRepository = userRepository;
	}

	public String getLayerServerUri() {
		return layerServerUri;
	}

	public void setLayerServerUri(String layerServerUriValue) {
		this.layerServerUri = layerServerUriValue;
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

	public void setDefaultMapCenterY(double defaultMapCenterLatitude) {
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

	public float getDefaultMapMinDepth() {
		return defaultMapMinDepth;
	}

	public void setDefaultMapMinDepth(float defaultMapMinDepth) {
		this.defaultMapMinDepth = defaultMapMinDepth;
	}

	public DepthLimitType getDefaultMapMaxDepthType() {
		return defaultMapMaxDepthType;
	}

	public void setDefaultMapMaxDepthType(DepthLimitType defaultMapMaxDepthType) {
		this.defaultMapMaxDepthType = defaultMapMaxDepthType;
	}

	public float getDefaultMapMaxDepth() {
		return defaultMapMaxDepth;
	}

	public void setDefaultMapMaxDepth(float defaultMapMaxDepth) {
		this.defaultMapMaxDepth = defaultMapMaxDepth;
	}

	public MagnitudeType getDefaultMapMagnitudeType() {
		return defaultMapMagnitudeType;
	}

	public void setDefaultMapMagnitudeType(MagnitudeType defaultMapMagnitudeType) {
		this.defaultMapMagnitudeType = defaultMapMagnitudeType;
	}

	public MagnitudeLimitType getDefaultMapMinMagnitudeType() {
		return defaultMapMinMagnitudeType;
	}

	public void setDefaultMapMinMagnitudeType(
			MagnitudeLimitType defaultMapMinMagnitudeType) {
		this.defaultMapMinMagnitudeType = defaultMapMinMagnitudeType;
	}

	public float getDefaultMapMinMagnitude() {
		return defaultMapMinMagnitude;
	}

	public void setDefaultMapMinMagnitude(float defaultMapMinMagnitude) {
		this.defaultMapMinMagnitude = defaultMapMinMagnitude;
	}

	public MagnitudeLimitType getDefaultMapMaxMagnitudeType() {
		return defaultMapMaxMagnitudeType;
	}

	public void setDefaultMapMaxMagnitudeType(
			MagnitudeLimitType defaultMapMaxMagnitudeType) {
		this.defaultMapMaxMagnitudeType = defaultMapMaxMagnitudeType;
	}

	public float getDefaultMapMaxMagnitude() {
		return defaultMapMaxMagnitude;
	}

	public void setDefaultMapMaxMagnitude(float defaultMapMaxMagnitude) {
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
		DateLimitType minDateType = defaultMapMinDateType;
		float minDateRelativeAmount = defaultMapMinDateRelativeAmount;
		DateUnits minDateRelativeUnits = defaultMapMinDateRelativeUnits;
		Date minDate = defaultMapMinDate;
		DateLimitType maxDateType = defaultMapMaxDateType;
		float maxDateRelativeAmount = defaultMapMaxDateRelativeAmount;
		DateUnits maxDateRelativeUnits = defaultMapMaxDateRelativeUnits;
		Date maxDate = defaultMapMaxDate;
		DepthLimitType minDepthType = defaultMapMinDepthType;
		float minDepth = defaultMapMinDepth;
		DepthLimitType maxDepthType = defaultMapMaxDepthType;
		float maxDepth = defaultMapMaxDepth;
		MagnitudeType magnitudeType = defaultMapMagnitudeType;
		MagnitudeLimitType minMagnitudeType = defaultMapMinMagnitudeType;
		float minMagnitude = defaultMapMinMagnitude;
		MagnitudeLimitType maxMagnitudeType = defaultMapMaxMagnitudeType;
		float maxMagnitude = defaultMapMaxMagnitude;
		boolean listUnmeasured = defaultMapListUnmeasured;
		AnimationType animationType = defaultMapAnimationType;
		float animationStepKeep = defaultMapAnimationStepKeep;
		int animationSteps = defaultMapAnimationSteps;
		float animationStepDuration = defaultMapAnimationStepDuration;
		boolean reverseAnimation = defaultMapReverseAnimation;

		if (minDate == null && maxDate == null) {
			Date currentDate = new Date();
			maxDate = currentDate;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			minDate = calendar.getTime();
		} else if (minDate == null && maxDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(maxDate);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			minDate = calendar.getTime();
		} else if (minDate != null && maxDate == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(minDate);
			calendar.add(Calendar.DAY_OF_MONTH, +1);
			maxDate = calendar.getTime();
		}
		Map map = new Map(name, description, center, zoom, minDateType,
				minDateRelativeAmount, minDateRelativeUnits, minDate,
				maxDateType, maxDateRelativeAmount, maxDateRelativeUnits,
				maxDate, minDepthType, minDepth, maxDepthType, maxDepth,
				magnitudeType, minMagnitudeType, minMagnitude,
				maxMagnitudeType, maxMagnitude, listUnmeasured, animationType,
				animationStepKeep, animationSteps, animationStepDuration,
				reverseAnimation);
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
		List<MapDto> mapDtos = DtoMarshaler.unmarshallMaps(user.getMaps());
		ListUserMapsResponseDto response = new ListUserMapsResponseDto(mapDtos);
		return response;
	}

	public GetLayerServerUriResponseDto getLayerServerUri(
			ActorCredentialsDto actorCredentials,
			GetLayerServerUriRequestDto request) {
		String layerServerUri = this.layerServerUri;
		GetLayerServerUriResponseDto response = new GetLayerServerUriResponseDto(
				layerServerUri);
		return response;
	}
}

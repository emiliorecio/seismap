package com.seismap.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.Application;
import com.seismap.model.entity.DataBounds;
import com.seismap.model.entity.Map;
import com.seismap.model.entity.MapServiceSettings;
import com.seismap.model.entity.Style;
import com.seismap.model.entity.User;
import com.seismap.model.repository.ApplicationRepository;
import com.seismap.model.repository.DataBoundsRepository;
import com.seismap.model.repository.MapRepository;
import com.seismap.model.repository.UserRepository;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;
import com.seismap.service.event.ExtendedMagnitudeType;
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

	private ApplicationRepository applicationRepository;
	private MapRepository mapRepository;
	private UserRepository userRepository;
	private DataBoundsRepository dataBoundsRepository;
	private GeometryFactory geometryFactory;

	protected MapServiceImpl() {
	}

	public MapServiceImpl(MapRepository mapRepository,
			DataBoundsRepository dataBoundsRepository,
			ApplicationRepository applicationRepository,
			UserRepository userRepository, GeometryFactory geometryFactory) {
		this.mapRepository = mapRepository;
		this.userRepository = userRepository;
		this.dataBoundsRepository = dataBoundsRepository;
		this.applicationRepository = applicationRepository;
		this.geometryFactory = geometryFactory;
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
		DataBounds dataBounds = dataBoundsRepository.fetch();
		Application application = applicationRepository.fetch();
		MapServiceSettings settings = application.getMapServiceSettings();
		String description = settings.getDefaultMapDescription();
		Point center = geometryFactory.createPoint(new Coordinate(settings
				.getDefaultMapCenterLongitude(), settings
				.getDefaultMapCenterLatitude()));
		int zoom = settings.getDefaultMapZoom();
		DateLimitType minDateType = settings.getDefaultMapMinDateType();
		float minDateRelativeAmount = settings
				.getDefaultMapMinDateRelativeAmount();
		DateUnits minDateRelativeUnits = settings
				.getDefaultMapMinDateRelativeUnits();
		Date minDate = settings.getDefaultMapMinDate() == null ? dataBounds
				.getMinDate() : settings.getDefaultMapMinDate();
		DateLimitType maxDateType = settings.getDefaultMapMaxDateType();
		float maxDateRelativeAmount = settings
				.getDefaultMapMaxDateRelativeAmount();
		DateUnits maxDateRelativeUnits = settings
				.getDefaultMapMaxDateRelativeUnits();
		Date maxDate = settings.getDefaultMapMaxDate() == null ? dataBounds
				.getMaxDate() : settings.getDefaultMapMaxDate();
		DepthLimitType minDepthType = settings.getDefaultMapMinDepthType();
		Float minDepth = settings.getDefaultMapMinDepth() == null ? dataBounds
				.getMinDepth() : settings.getDefaultMapMinDepth();
		DepthLimitType maxDepthType = settings.getDefaultMapMaxDepthType();
		Float maxDepth = settings.getDefaultMapMaxDepth() == null ? dataBounds
				.getMaxDepth() : settings.getDefaultMapMaxDepth();
		ExtendedMagnitudeType magnitudeType = settings
				.getDefaultMapMagnitudeType();
		MagnitudeLimitType minMagnitudeType = settings
				.getDefaultMapMinMagnitudeType();
		Float minMagnitude = settings.getDefaultMapMinMagnitude() == null ? dataBounds
				.getMagnitudeBounds().get(magnitudeType).getMin()
				: settings.getDefaultMapMinMagnitude();
		MagnitudeLimitType maxMagnitudeType = settings
				.getDefaultMapMaxMagnitudeType();
		Float maxMagnitude = settings.getDefaultMapMaxMagnitude() == null ? dataBounds
				.getMagnitudeBounds().get(magnitudeType).getMax()
				: settings.getDefaultMapMaxMagnitude();
		boolean listUnmeasured = settings.isDefaultMapListUnmeasured();
		AnimationType animationType = settings.getDefaultMapAnimationType();
		float animationStepKeep = settings.getDefaultMapAnimationStepKeep();
		int animationSteps = settings.getDefaultMapAnimationSteps();
		float animationStepDuration = settings
				.getDefaultMapAnimationStepDuration();
		boolean reverseAnimation = settings.isDefaultMapReverseAnimation();
		Style style = settings.getDefaultMapStyle();

		if (minDepth == null) {
			minDepth = Float.valueOf(0f);
		}
		if (maxDepth == null) {
			maxDepth = Float.valueOf(1000f);
		}
		if (minMagnitude == null) {
			minMagnitude = Float.valueOf(0f);
		}
		if (maxMagnitude == null) {
			maxMagnitude = Float.valueOf(100f);
		}
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
				maxDate, minDepthType, minDepth.floatValue(), maxDepthType,
				maxDepth.floatValue(), magnitudeType, minMagnitudeType,
				minMagnitude.floatValue(), maxMagnitudeType,
				maxMagnitude.floatValue(), listUnmeasured, animationType,
				animationStepKeep, animationSteps, animationStepDuration,
				reverseAnimation, style);
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

	@Transactional
	public GetLayerServerUriResponseDto getLayerServerUri(
			ActorCredentialsDto actorCredentials,
			GetLayerServerUriRequestDto request) {
		Application application = applicationRepository.fetch();
		MapServiceSettings settings = application.getMapServiceSettings();

		String layerServerUri = settings.getLayerServerUri();
		GetLayerServerUriResponseDto response = new GetLayerServerUriResponseDto(
				layerServerUri);
		return response;
	}
}

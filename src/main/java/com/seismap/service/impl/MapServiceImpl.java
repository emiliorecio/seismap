package com.seismap.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import com.seismap.model.repository.StyleRepository;
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
import com.seismap.service.map.GetDefaultMapRequestDto;
import com.seismap.service.map.GetDefaultMapResponseDto;
import com.seismap.service.map.GetLayerServerUriRequestDto;
import com.seismap.service.map.GetLayerServerUriResponseDto;
import com.seismap.service.map.GetLegendRequestDto;
import com.seismap.service.map.GetMapRequestDto;
import com.seismap.service.map.GetMapResponseDto;
import com.seismap.service.map.ListUserMapsRequestDto;
import com.seismap.service.map.ListUserMapsResponseDto;
import com.seismap.service.map.MagnitudeLimitType;
import com.seismap.service.map.MapDto;
import com.seismap.service.map.MapService;
import com.seismap.service.map.ModifiableMapDataDto;
import com.seismap.service.map.ModifyMapRequestDto;
import com.seismap.service.map.ModifyMapResponseDto;
import com.seismap.service.map.RenameMapRequestDto;
import com.seismap.service.map.RenameMapResponseDto;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class MapServiceImpl implements MapService {

	private ApplicationRepository applicationRepository;
	private MapRepository mapRepository;
	private UserRepository userRepository;
	private DataBoundsRepository dataBoundsRepository;
	private StyleRepository styleRepository;
	private GeometryFactory geometryFactory;

	protected MapServiceImpl() {
	}

	public MapServiceImpl(MapRepository mapRepository,
			DataBoundsRepository dataBoundsRepository,
			ApplicationRepository applicationRepository,
			UserRepository userRepository, StyleRepository styleRepository,
			GeometryFactory geometryFactory) {
		this.mapRepository = mapRepository;
		this.userRepository = userRepository;
		this.dataBoundsRepository = dataBoundsRepository;
		this.applicationRepository = applicationRepository;
		this.styleRepository = styleRepository;
		this.geometryFactory = geometryFactory;
	}

	@Transactional
	public GetDefaultMapResponseDto getDefault(
			ActorCredentialsDto actorCredentials,
			GetDefaultMapRequestDto request) {
		DataBounds dataBounds = dataBoundsRepository.fetch();
		Application application = applicationRepository.fetch();
		MapServiceSettings settings = application.getMapServiceSettings();
		String name = settings.getDefaultMapName();
		String description = settings.getDefaultMapDescription();
		double centerLongitude = settings.getDefaultMapCenterLongitude();
		double centerLatitude = settings.getDefaultMapCenterLatitude();
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

		ModifiableMapDataDto mapDto = new ModifiableMapDataDto(name,
				description, Double.valueOf(centerLatitude),
				Double.valueOf(centerLongitude), Integer.valueOf(zoom),
				minDateType, Float.valueOf(minDateRelativeAmount),
				minDateRelativeUnits, minDate, maxDateType,
				Float.valueOf(maxDateRelativeAmount), maxDateRelativeUnits,
				maxDate, minDepthType, minDepth, maxDepthType, maxDepth,
				magnitudeType, minMagnitudeType, minMagnitude,
				maxMagnitudeType, maxMagnitude,
				Boolean.valueOf(listUnmeasured), animationType,
				Float.valueOf(animationStepKeep),
				Integer.valueOf(animationSteps),
				Float.valueOf(animationStepDuration),
				Boolean.valueOf(reverseAnimation), style.getId());

		GetDefaultMapResponseDto response = new GetDefaultMapResponseDto(mapDto);
		return response;
	}

	@Transactional
	public CreateMapResponseDto create(ActorCredentialsDto actorCredentials,
			CreateMapRequestDto request) {
		String name = request.getMap().getName();
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
		if (!userId.equals(actorCredentials.getUserId())) {
			CreateMapResponseDto exceptionResponse = new CreateMapResponseDto(
					ExceptionCause.UNAUTHORIZED, "El usuario " + userId
							+ " no tiene permiso para realizar la operación.");
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
		ModifiableMapDataDto mapDataDto = request.getMap();
		Long styleId = mapDataDto.getStyleId();
		Style style = styleRepository.get(styleId);
		if (style == null) {
			CreateMapResponseDto exceptionResponse = new CreateMapResponseDto(
					ExceptionCause.NO_STYLE_WITH_GIVEN_ID, "El estilo "
							+ styleId + " no existe.");
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.STYLE_ID, styleId);
			return exceptionResponse;
		}
		Point center = geometryFactory.createPoint(new Coordinate(mapDataDto
				.getCenterLongitude().doubleValue(), mapDataDto
				.getCenterLatitude().doubleValue()));
		Map map = new Map(user, name, mapDataDto.getDescription(), center,
				mapDataDto.getZoom().intValue(), mapDataDto.getMinDateType(),
				mapDataDto.getMinDateRelativeAmount().floatValue(),
				mapDataDto.getMinDateRelativeUnits(), mapDataDto.getMinDate(),
				mapDataDto.getMaxDateType(), mapDataDto
						.getMaxDateRelativeAmount().floatValue(),
				mapDataDto.getMaxDateRelativeUnits(), mapDataDto.getMaxDate(),
				mapDataDto.getMinDepthType(), mapDataDto.getMinDepth()
						.floatValue(), mapDataDto.getMaxDepthType(), mapDataDto
						.getMaxDepth().floatValue(),
				mapDataDto.getMagnitudeType(),
				mapDataDto.getMinMagnitudeType(), mapDataDto.getMinMagnitude()
						.floatValue(), mapDataDto.getMaxMagnitudeType(),
				mapDataDto.getMaxMagnitude().floatValue(), mapDataDto
						.getListUnmeasured().booleanValue(),
				mapDataDto.getAnimationType(), mapDataDto
						.getAnimationStepKeep().floatValue(), mapDataDto
						.getAnimationSteps().intValue(), mapDataDto
						.getAnimationStepDuration().floatValue(), mapDataDto
						.getReverseAnimation().booleanValue(), style);
		user.getMapsManager().add(map);
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
		Long userId = request.getUserId();
		if (!userId.equals(actorCredentials.getUserId())) {
			ListUserMapsResponseDto exceptionResponse = new ListUserMapsResponseDto(
					ExceptionCause.UNAUTHORIZED, "El usuario " + userId
							+ " no tiene permiso para realizar la operación.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.USER_ID,
					userId);
			return exceptionResponse;
		}
		User user = userRepository.get(userId);
		if (user == null) {
			ListUserMapsResponseDto exceptionResponse = new ListUserMapsResponseDto(
					ExceptionCause.NO_USER_WITH_GIVEN_ID, "El usuario "
							+ userId + " no existe.");
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

	@Transactional
	public ModifyMapResponseDto modify(ActorCredentialsDto actorCredentials,
			ModifyMapRequestDto request) {
		Long mapId = request.getMapId();
		Map map = mapRepository.get(mapId);
		if (map == null) {
			ModifyMapResponseDto exceptionResponse = new ModifyMapResponseDto(
					ExceptionCause.NO_MAP_WITH_GIVEN_ID, "El mapa " + mapId
							+ " no existe.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.MAP_ID,
					mapId);
			return exceptionResponse;
		}
		Long userId = actorCredentials.getUserId();
		if (!map.getUser().getId().equals(actorCredentials.getUserId())) {
			ModifyMapResponseDto exceptionResponse = new ModifyMapResponseDto(
					ExceptionCause.UNAUTHORIZED, "El usuario " + userId
							+ " no tiene permiso para realizar la operación.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.USER_ID,
					userId);
			return exceptionResponse;
		}
		ModifiableMapDataDto mapDataDto = request.getMap();
		String name = mapDataDto.getName();
		Map existingMap = mapRepository.getByUserAndName(map.getUser(), name);
		if (existingMap != null && existingMap != map) {
			String userEmail = map.getUser().getEmail();
			ModifyMapResponseDto exceptionResponse = new ModifyMapResponseDto(
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
		map.setName(name);
		Long styleId = mapDataDto.getStyleId();
		Style style = styleRepository.get(styleId);
		if (style == null) {
			ModifyMapResponseDto exceptionResponse = new ModifyMapResponseDto(
					ExceptionCause.NO_STYLE_WITH_GIVEN_ID, "El estilo "
							+ styleId + " no existe.");
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.STYLE_ID, mapId);
			return exceptionResponse;
		}
		map.setDescription(mapDataDto.getDescription());
		Point center = geometryFactory.createPoint(new Coordinate(mapDataDto
				.getCenterLongitude().doubleValue(), mapDataDto
				.getCenterLatitude().doubleValue()));
		map.setCenter(center);
		map.setZoom(mapDataDto.getZoom().intValue());
		map.setMinDateType(mapDataDto.getMinDateType());
		map.setMinDateRelativeAmount(mapDataDto.getMinDateRelativeAmount()
				.floatValue());
		map.setMinDateRelativeUnits(mapDataDto.getMinDateRelativeUnits());
		map.setMinDate(mapDataDto.getMinDate());
		map.setMaxDateRelativeAmount(mapDataDto.getMaxDateRelativeAmount()
				.floatValue());
		map.setMaxDateRelativeUnits(mapDataDto.getMaxDateRelativeUnits());
		map.setMaxDateType(mapDataDto.getMaxDateType());
		map.setMaxDate(mapDataDto.getMaxDate());
		map.setMinDepthType(mapDataDto.getMinDepthType());
		map.setMinDepth(mapDataDto.getMinDepth().floatValue());
		map.setMaxDepthType(mapDataDto.getMaxDepthType());
		map.setMaxDepth(mapDataDto.getMaxDepth().floatValue());
		map.setMagnitudeType(mapDataDto.getMagnitudeType());
		map.setMinMagnitudeType(mapDataDto.getMinMagnitudeType());
		map.setMinMagnitude(mapDataDto.getMinMagnitude().floatValue());
		map.setMaxMagnitudeType(mapDataDto.getMaxMagnitudeType());
		map.setMaxMagnitude(mapDataDto.getMaxMagnitude().floatValue());
		map.setListUnmeasured(mapDataDto.getListUnmeasured().booleanValue());
		map.setAnimationType(mapDataDto.getAnimationType());
		map.setAnimationStepKeep(mapDataDto.getAnimationStepKeep().floatValue());
		map.setAnimationSteps(mapDataDto.getAnimationSteps().intValue());
		map.setAnimationStepDuration(mapDataDto.getAnimationStepDuration()
				.floatValue());
		map.setReverseAnimation(mapDataDto.getReverseAnimation().booleanValue());
		map.setStyle(style);
		MapDto mapDto = DtoMarshaler.unmarshallMap(map);
		ModifyMapResponseDto response = new ModifyMapResponseDto(mapDto);
		return response;
	}

	@Transactional
	public RenameMapResponseDto rename(ActorCredentialsDto actorCredentials,
			RenameMapRequestDto request) {
		Long mapId = request.getMapId();
		Map map = mapRepository.get(mapId);
		if (map == null) {
			RenameMapResponseDto exceptionResponse = new RenameMapResponseDto(
					ExceptionCause.NO_MAP_WITH_GIVEN_ID, "El mapa " + mapId
							+ " no existe.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.MAP_ID,
					mapId);
			return exceptionResponse;
		}
		Long userId = actorCredentials.getUserId();
		User user = map.getUser();
		if (!user.getId().equals(actorCredentials.getUserId())) {
			RenameMapResponseDto exceptionResponse = new RenameMapResponseDto(
					ExceptionCause.UNAUTHORIZED, "El usuario " + userId
							+ " no tiene permiso para realizar la operación.");
			exceptionResponse.addExceptionParameter(ExceptionParameter.USER_ID,
					userId);
			return exceptionResponse;
		}
		String name = request.getMapName();
		Map existingMap = mapRepository.getByUserAndName(map.getUser(), name);
		if (existingMap != null && existingMap != map) {
			String userEmail = user.getEmail();
			RenameMapResponseDto exceptionResponse = new RenameMapResponseDto(
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
		map.setName(name);
		MapDto mapDto = DtoMarshaler.unmarshallMap(map);
		RenameMapResponseDto response = new RenameMapResponseDto(mapDto);
		return response;
	}

	@Transactional
	public Resource getLegend(ActorCredentialsDto actorCredentials,
			GetLegendRequestDto request) {
		String sld = request.getSld();

		Application application = applicationRepository.fetch();
		MapServiceSettings settings = application.getMapServiceSettings();

		String legendsDirectory = settings.getLegendsDirectory();
		File legendsDirectoryFile = new File(legendsDirectory);
		File legendFile;
		try {
			legendFile = new File(legendsDirectory, sld + ".png")
					.getCanonicalFile();
		} catch (IOException e) {
			throw new RuntimeException("La leyenda no es válida: " + e, e);
		}
		if (!legendFile.exists()
				|| !legendFile.getParentFile().equals(legendsDirectoryFile)) {
			throw new RuntimeException("La leyenda no es válida.");
		}
		return new FileSystemResource(legendFile);
	}
}

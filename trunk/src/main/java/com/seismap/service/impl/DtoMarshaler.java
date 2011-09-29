package com.seismap.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.seismap.model.entity.Agency;
import com.seismap.model.entity.Category;
import com.seismap.model.entity.DataBounds;
import com.seismap.model.entity.Event;
import com.seismap.model.entity.EventAndAverageMagnitudes;
import com.seismap.model.entity.Magnitude;
import com.seismap.model.entity.MagnitudeDataBounds;
import com.seismap.model.entity.MagnitudeLimits;
import com.seismap.model.entity.Map;
import com.seismap.model.entity.Style;
import com.seismap.model.entity.User;
import com.seismap.service.category.CategoryDto;
import com.seismap.service.event.AgencyDto;
import com.seismap.service.event.DataBoundsDto;
import com.seismap.service.event.EventAndAverageMagnitudesDto;
import com.seismap.service.event.EventDto;
import com.seismap.service.event.EventInfoAndAverageMagnitudesDto;
import com.seismap.service.event.ExtendedMagnitudeType;
import com.seismap.service.event.MagnitudeDataBoundsDto;
import com.seismap.service.event.MagnitudeDto;
import com.seismap.service.event.MagnitudeLimitsDto;
import com.seismap.service.map.MapDto;
import com.seismap.service.style.StyleDto;
import com.seismap.service.user.UserDto;

class DtoMarshaler {

	public static EventAndAverageMagnitudesDto unmarshallEvent(Event event,
			EventAndAverageMagnitudes eventAndAverageMagnitudes) {
		return new EventAndAverageMagnitudesDto(event.getId(),
				Double.valueOf(event.getLatitude()), Double.valueOf(event
						.getLongitude()), Float.valueOf(event.getDepth()),
				event.getDate(), event.getName(), event.getNotes(),
				event.getReference(),
				eventAndAverageMagnitudes.getRANKMagnitude(),
				eventAndAverageMagnitudes.getMLMagnitude(),
				eventAndAverageMagnitudes.getMBMagnitude(),
				eventAndAverageMagnitudes.getMSMagnitude(),
				eventAndAverageMagnitudes.getMWMagnitude(),
				eventAndAverageMagnitudes.getMBLGMagnitude(),
				eventAndAverageMagnitudes.getMCMagnitude(),
				unmarshallMagnitudes(event.getMagnitudes()));
	}

	public static EventDto unmarshallEvent(Event event) {
		return new EventDto(event.getId(), Double.valueOf(event.getLatitude()),
				Double.valueOf(event.getLongitude()), Float.valueOf(event
						.getDepth()), event.getDate(), event.getName(),
				event.getNotes(), event.getReference(),
				unmarshallMagnitudes(event.getMagnitudes()));
	}

	public static List<EventDto> unmarshallEvents(List<Event> events) {
		List<EventDto> eventDtos = new ArrayList<EventDto>(events.size());
		for (Event event : events) {
			eventDtos.add(unmarshallEvent(event));
		}
		return eventDtos;
	}

	public static MagnitudeDto unmarshallMagnitude(Magnitude magnitude) {
		return new MagnitudeDto(magnitude.getId(), magnitude.getType(),
				Float.valueOf(magnitude.getValue()),
				unmarshallAgency(magnitude.getReportingAgency()));
	}

	public static List<MagnitudeDto> unmarshallMagnitudes(
			List<Magnitude> magnitudes) {
		List<MagnitudeDto> magnitudeDtos = new ArrayList<MagnitudeDto>(
				magnitudes.size());
		for (Magnitude magnitude : magnitudes) {
			magnitudeDtos.add(unmarshallMagnitude(magnitude));
		}
		return magnitudeDtos;
	}

	public static AgencyDto unmarshallAgency(Agency agency) {
		return new AgencyDto(agency.getId(), agency.getCode());
	}

	public static List<AgencyDto> unmarshallAgencies(List<Agency> agencies) {
		List<AgencyDto> agencyDtos = new ArrayList<AgencyDto>(agencies.size());
		for (Agency agency : agencies) {
			agencyDtos.add(unmarshallAgency(agency));
		}
		return agencyDtos;
	}

	public static EventInfoAndAverageMagnitudesDto unmarshallEventAndMagnitudes(
			EventAndAverageMagnitudes eventAndAverageMagnitudes) {
		return new EventInfoAndAverageMagnitudesDto(
				eventAndAverageMagnitudes.getId(),
				Double.valueOf(eventAndAverageMagnitudes.getLatitude()),
				Double.valueOf(eventAndAverageMagnitudes.getLongitude()),
				Float.valueOf(eventAndAverageMagnitudes.getDepth()),
				eventAndAverageMagnitudes.getDate(),
				eventAndAverageMagnitudes.getName(),
				eventAndAverageMagnitudes.getNotes(),
				eventAndAverageMagnitudes.getReference(),
				eventAndAverageMagnitudes.getRANKMagnitude(),
				eventAndAverageMagnitudes.getMLMagnitude(),
				eventAndAverageMagnitudes.getMBLGMagnitude(),
				eventAndAverageMagnitudes.getMSMagnitude(),
				eventAndAverageMagnitudes.getMWMagnitude(),
				eventAndAverageMagnitudes.getMBLGMagnitude(),
				eventAndAverageMagnitudes.getMCMagnitude());
	}

	public static List<EventInfoAndAverageMagnitudesDto> unmarshallEventsAndAverageMagnitudes(
			List<EventAndAverageMagnitudes> eventsAndAverageMagnitudes) {
		List<EventInfoAndAverageMagnitudesDto> eventAndAverageMagnitudeDtos = new ArrayList<EventInfoAndAverageMagnitudesDto>(
				eventsAndAverageMagnitudes.size());
		for (EventAndAverageMagnitudes eventAndAverageMagnitude : eventsAndAverageMagnitudes) {
			eventAndAverageMagnitudeDtos
					.add(unmarshallEventAndMagnitudes(eventAndAverageMagnitude));
		}
		return eventAndAverageMagnitudeDtos;
	}

	public static MapDto unmarshallMap(Map map) {
		return new MapDto(map.getId(), map.getName(), map.getDescription(),
				Double.valueOf(map.getCenterLatitude()), Double.valueOf(map
						.getCenterLongitude()), Integer.valueOf(map.getZoom()),
				map.getMinDateType(), Float.valueOf(map
						.getMinDateRelativeAmount()),
				map.getMinDateRelativeUnits(), map.getMinDate(),
				map.getMaxDateType(), Float.valueOf(map
						.getMaxDateRelativeAmount()),
				map.getMaxDateRelativeUnits(), map.getMaxDate(),
				map.getMinDepthType(), Float.valueOf(map.getMinDepth()),
				map.getMaxDepthType(), Float.valueOf(map.getMaxDepth()),
				map.getMagnitudeType(), map.getMinMagnitudeType(),
				Float.valueOf(map.getMinMagnitude()),
				map.getMaxMagnitudeType(),
				Float.valueOf(map.getMaxMagnitude()), Boolean.valueOf(map
						.isListUnmeasured()), map.getAnimationType(),
				Float.valueOf(map.getAnimationStepKeep()), Integer.valueOf(map
						.getAnimationSteps()), Float.valueOf(map
						.getAnimationStepDuration()), Boolean.valueOf(map
						.isReverseAnimation()), map.getStyle().getId());
	}

	public static List<MapDto> unmarshallMaps(List<Map> maps) {
		List<MapDto> mapDtos = new ArrayList<MapDto>(maps.size());
		for (Map map : maps) {
			mapDtos.add(unmarshallMap(map));
		}
		return mapDtos;
	}

	public static CategoryDto unmarshallCategory(Category category) {
		return new CategoryDto(category.getId(), category.getName(),
				unmarshallMaps(category.getMaps()));
	}

	public static List<CategoryDto> unmarshallCategories(
			List<Category> categories) {
		List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>(
				categories.size());
		for (Category category : categories) {
			categoryDtos.add(unmarshallCategory(category));
		}
		return categoryDtos;
	}

	public static StyleDto unmarshallStyle(Style style) {
		return new StyleDto(style.getId(), style.getSld(), style.getName(),
				style.getVariables());
	}

	public static List<StyleDto> unmarshallStyles(List<Style> styles) {
		List<StyleDto> styleDtos = new ArrayList<StyleDto>(styles.size());
		for (Style style : styles) {
			styleDtos.add(unmarshallStyle(style));
		}
		return styleDtos;
	}

	public static UserDto unmarshallUser(User user) {
		return new UserDto(user.getId(), user.getName(), user.getEmail(),
				Boolean.valueOf(user.isAdministrator()));
	}

	public static List<UserDto> unmarshallUsers(List<User> users) {
		List<UserDto> userDtos = new ArrayList<UserDto>(users.size());
		for (User user : users) {
			userDtos.add(unmarshallUser(user));
		}
		return userDtos;
	}

	public static DataBoundsDto unmarshallDataBounds(DataBounds dataBounds) {
		return new DataBoundsDto(dataBounds.getId(), dataBounds.getMinDate(),
				dataBounds.getMaxDate(), dataBounds.getMinDepth(),
				dataBounds.getMaxDepth(),
				unmarshallMagnitudeDataBounds(dataBounds.getMagnitudeBounds()));
	}

	public static java.util.Map<ExtendedMagnitudeType, MagnitudeDataBoundsDto> unmarshallMagnitudeDataBounds(
			java.util.Map<ExtendedMagnitudeType, MagnitudeDataBounds> magnitudeDataBoundsMap) {
		java.util.Map<ExtendedMagnitudeType, MagnitudeDataBoundsDto> magnitudeDataBoundsDtosMap = new LinkedHashMap<ExtendedMagnitudeType, MagnitudeDataBoundsDto>(
				magnitudeDataBoundsMap.size());
		for (java.util.Map.Entry<ExtendedMagnitudeType, MagnitudeDataBounds> magnitudeDataBoundsEntry : magnitudeDataBoundsMap
				.entrySet()) {
			ExtendedMagnitudeType magnitudeType = magnitudeDataBoundsEntry
					.getKey();
			MagnitudeDataBounds magnitudeDataBounds = magnitudeDataBoundsEntry
					.getValue();
			magnitudeDataBoundsDtosMap.put(magnitudeType,
					unmarshallMagnitudeDataBounds(magnitudeDataBounds));
		}
		return magnitudeDataBoundsDtosMap;
	}

	public static MagnitudeDataBoundsDto unmarshallMagnitudeDataBounds(
			MagnitudeDataBounds magnitudeDataBounds) {
		return new MagnitudeDataBoundsDto(
				magnitudeDataBounds.getMagnitudeType(),
				magnitudeDataBounds.getMin(), magnitudeDataBounds.getMax());
	}

	public static List<MagnitudeLimitsDto> unmarshallMagnitudeLimits(
			List<MagnitudeLimits> magnitudeLimitsList) {
		List<MagnitudeLimitsDto> magnitudeLimitsDtos = new ArrayList<MagnitudeLimitsDto>(
				magnitudeLimitsList.size());
		for (MagnitudeLimits magnitudeLimits : magnitudeLimitsList) {
			magnitudeLimitsDtos.add(unmarshallMagnitudeLimits(magnitudeLimits));
		}
		return magnitudeLimitsDtos;
	}

	public static MagnitudeLimitsDto unmarshallMagnitudeLimits(
			MagnitudeLimits magnitudeLimits) {
		return new MagnitudeLimitsDto(magnitudeLimits.getMagnitudeType(),
				Float.valueOf(magnitudeLimits.getMin()),
				Float.valueOf(magnitudeLimits.getMax()));
	}
}

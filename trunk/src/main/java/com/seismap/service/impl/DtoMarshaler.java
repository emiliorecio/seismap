package com.seismap.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.seismap.model.entity.Agency;
import com.seismap.model.entity.Category;
import com.seismap.model.entity.Event;
import com.seismap.model.entity.EventAndAverageMagnitude;
import com.seismap.model.entity.Magnitude;
import com.seismap.model.entity.Map;
import com.seismap.model.entity.User;
import com.seismap.service.category.CategoryDto;
import com.seismap.service.event.AgencyDto;
import com.seismap.service.event.EventAndAverageMagnitudeDto;
import com.seismap.service.event.EventDto;
import com.seismap.service.event.MagnitudeDto;
import com.seismap.service.map.MapDto;
import com.seismap.service.user.UserDto;

class DtoMarshaler {

	public static EventDto unmarshallEvent(Event event) {
		return new EventDto(event.getId(), Double.valueOf(event.getLatitude()),
				Double.valueOf(event.getLongitude()), Float.valueOf(event
						.getDepth()), event.getDate(),
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

	public static EventAndAverageMagnitudeDto unmarshallEventAndMagnitude(
			EventAndAverageMagnitude eventAndAverageMagnitude) {
		return new EventAndAverageMagnitudeDto(
				eventAndAverageMagnitude.getEventId(),
				Double.valueOf(eventAndAverageMagnitude.getEventLatitude()),
				Double.valueOf(eventAndAverageMagnitude.getEventLongitude()),
				Float.valueOf(eventAndAverageMagnitude.getEventDepth()),
				eventAndAverageMagnitude.getEventDate(),
				eventAndAverageMagnitude.getMagnitudeType(),
				eventAndAverageMagnitude.getMagnitudeAverageValue());
	}

	public static List<EventAndAverageMagnitudeDto> unmarshallEventsAndAverageMagnitudes(
			List<EventAndAverageMagnitude> eventsAndAverageMagnitudes) {
		List<EventAndAverageMagnitudeDto> eventAndAverageMagnitudeDtos = new ArrayList<EventAndAverageMagnitudeDto>(
				eventsAndAverageMagnitudes.size());
		for (EventAndAverageMagnitude eventAndAverageMagnitude : eventsAndAverageMagnitudes) {
			eventAndAverageMagnitudeDtos
					.add(unmarshallEventAndMagnitude(eventAndAverageMagnitude));
		}
		return eventAndAverageMagnitudeDtos;
	}

	public static MapDto unmarshallMap(Map map) {
		return new MapDto(map.getId(), map.getName(), map.getDescription(),
				Double.valueOf(map.getCenterLatitude()), Double.valueOf(map
						.getCenterLongitude()), Integer.valueOf(map.getZoom()),
				map.getMinDateRelativeAmount(), map.getMinDateRelativeUnits(),
				map.getMinDate(), map.getMaxDateRelativeAmount(),
				map.getMaxDateRelativeUnits(), map.getMaxDate(),
				map.getMinDepth(), map.getMaxDepth(), map.getMagnitudeType(),
				map.getMinMagnitude(), map.getMaxMagnitude(),
				Boolean.valueOf(map.isListUnmeasured()),
				map.getAnimationType(), map.getAnimationStepKeep(),
				Integer.valueOf(map.getAnimationSteps()), Boolean.valueOf(map
						.isReverseAnimation()));
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
				category.getInApplicationIndex(), unmarshallMaps(category.getMaps()));
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

	public static UserDto unmarshallUser(User user) {
		return new UserDto(user.getId(), user.getName(), user.getEmail());
	}
}

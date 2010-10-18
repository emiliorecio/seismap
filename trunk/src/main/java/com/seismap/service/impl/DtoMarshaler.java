package com.seismap.service.impl;

import java.util.EnumMap;
import java.util.Map;

import com.seismap.model.entity.Agency;
import com.seismap.model.entity.Event;
import com.seismap.model.entity.Magnitude;
import com.seismap.model.entity.MagnitudeType;
import com.seismap.model.repository.Range;
import com.seismap.service.AgencyDto;
import com.seismap.service.EventDto;
import com.seismap.service.MagnitudeDto;
import com.seismap.service.RangeDto;

class DtoMarshaler {

	public static EventDto unmarshall(Event event) {
		return new EventDto(event.getId(), event.getLatitude(), event
				.getLongitude(), event.getDepth(), event.getDate(),
				unmarshall(event.getMagnitudes()));
	}

	public static EventDto[] unmarshall(Event[] events) {
		EventDto[] eventDtos = new EventDto[events.length];
		int index = 0;
		for (Event event : events) {
			eventDtos[index++] = unmarshall(event);
		}
		return eventDtos;
	}

	public static MagnitudeDto unmarshall(Magnitude magnitude) {
		return new MagnitudeDto(magnitude.getId(), magnitude.getType(),
				magnitude.getValue(),
				unmarshall(magnitude.getReportingAgency()));
	}

	public static MagnitudeDto[] unmarshall(Magnitude[] magnitudes) {
		MagnitudeDto[] magnitudeDtos = new MagnitudeDto[magnitudes.length];
		int index = 0;
		for (Magnitude magnitude : magnitudes) {
			magnitudeDtos[index++] = unmarshall(magnitude);
		}
		return magnitudeDtos;
	}

	public static AgencyDto unmarshall(Agency agency) {
		return new AgencyDto(agency.getId(), agency.getCode());
	}

	public static AgencyDto[] unmarshall(Agency[] agencies) {
		AgencyDto[] agencyDtos = new AgencyDto[agencies.length];
		int index = 0;
		for (Agency agency : agencies) {
			agencyDtos[index++] = unmarshall(agency);
		}
		return agencyDtos;
	}

	@SuppressWarnings("unchecked")
	public static <T> Range<T> marshall(RangeDto<T> rangeDto) {
		return (Range<T>) new Range<Object>(rangeDto.getMinimum(), rangeDto
				.getMaximum());
	}

	public static Map<MagnitudeType, Range<Float>> marshallMagnitudeRange(
			Map<MagnitudeType, RangeDto<Float>> rangeDtos) {
		Map<MagnitudeType, Range<Float>> ranges = new EnumMap<MagnitudeType, Range<Float>>(
				MagnitudeType.class);
		for (Map.Entry<MagnitudeType, RangeDto<Float>> rangeDtoEntry : rangeDtos
				.entrySet()) {
			ranges.put(rangeDtoEntry.getKey(), marshall(rangeDtoEntry
					.getValue()));
		}
		return ranges;
	}
}

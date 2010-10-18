package com.seismap.model.repository;

import java.util.Date;
import java.util.Map;

import com.seismap.model.entity.Event;
import com.seismap.model.entity.MagnitudeType;

public interface EventRepository {

	void put(Event event);

	Event[] get(Range<Date> dateRange, Range<Float> latitudeRange,
			Range<Float> longitudeRange, Range<Float> depthRange,
			Map<MagnitudeType, Range<Float>> magnitudeRanges);

}

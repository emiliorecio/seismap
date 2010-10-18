package com.seismap.model.repository.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.seismap.BaseIntegrationTest;
import com.seismap.model.entity.Agency;
import com.seismap.model.entity.Event;
import com.seismap.model.entity.Magnitude;
import com.seismap.model.entity.MagnitudeType;
import com.seismap.model.repository.EventRepository;
import com.seismap.model.repository.Range;

public class EventRepositoryTest extends BaseIntegrationTest {

	@Autowired
	private EventRepository repository;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	private Event event1;

	private Event event2;

	private Map<MagnitudeType, Range<Float>> createMagnitudeRangesMap() {
		Map<MagnitudeType, Range<Float>> map = new EnumMap<MagnitudeType, Range<Float>>(
				MagnitudeType.class);
		for (MagnitudeType type : MagnitudeType.values()) {
			map.put(type, new Range<Float>(null, null));
		}
		return map;
	}

	@Test
	@Transactional
	public void put() {
		Agency agency = new Agency("ABCD_" + System.currentTimeMillis());
		hibernateTemplate.save(agency);
		Magnitude magnitude = new Magnitude(agency, MagnitudeType.MB, 1.2f);
		List<Magnitude> magnitudes = Collections.singletonList(magnitude);
		Event event = new Event(1.2f, 3.4f, 5.6f, new Date(), magnitudes);
		repository.put(event);
	}

	private void loadData(Date date1, Date date2, Float latitude1,
			Float latitude2, Float longitude1, Float longitude2, Float depth1,
			Float depth2, MagnitudeType magnitudeType1, Float magnitudeValue1,
			MagnitudeType magnitudeType2a, Float magnitudeValue2a,
			MagnitudeType magnitudeType2b, Float magnitudeValue2b) {
		Agency agency1 = new Agency("ABCD1_" + System.currentTimeMillis());
		hibernateTemplate.save(agency1);
		Agency agency2 = new Agency("ABCD2_" + System.currentTimeMillis());
		hibernateTemplate.save(agency2);
		Magnitude magnitude1 = new Magnitude(agency1,
				magnitudeType1 == null ? MagnitudeType.MB : magnitudeType1,
				magnitudeValue1 == null ? 1.0f : magnitudeValue1);
		Magnitude magnitude2a = new Magnitude(agency2,
				magnitudeType2a == null ? MagnitudeType.MB : magnitudeType2a,
				magnitudeValue2a == null ? 1.0f : magnitudeValue2a);
		Magnitude magnitude2b = new Magnitude(agency1,
				magnitudeType2b == null ? MagnitudeType.MB : magnitudeType2b,
				magnitudeValue2b == null ? 1.0f : magnitudeValue2b);
		event1 = new Event(latitude1 == null ? 1.0f : latitude1,
				latitude1 == null ? 1.0f : latitude1, longitude1 == null ? 1.0f
						: longitude1, date1 == null ? new Date() : date1,
				Arrays.asList(new Magnitude[] { magnitude1 }));
		hibernateTemplate.save(event1);
		event2 = new Event(latitude2 == null ? 1.0f : latitude2,
				longitude2 == null ? 1.0f : longitude2, depth2 == null ? 1.0f
						: depth2, date2 == null ? new Date() : date2, Arrays
						.asList(new Magnitude[] { magnitude2a, magnitude2b }));
		hibernateTemplate.save(event2);
	}

	private void contains(Event[] events, Event event) {
		for (Event listEvent : events) {
			if (listEvent.equals(event)) {
				return;
			}
		}
		Assert.fail();
	}

	private void notContains(Event[] events, Event event) {
		for (Event listEvent : events) {
			if (listEvent.equals(event)) {
				Assert.fail();
			}
		}

	}

	@Test
	@Transactional
	public void getWithNoFilter() {
		loadData(null, null, null, null, null, null, null, null, null, null,
				null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, null), createMagnitudeRangesMap());
		Assert.assertEquals(2, events.length);
		contains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithSinceDateFilter() {
		Date date1 = new Date();
		Date sinceDate = new Date(date1.getTime() + 60000);
		Date date2 = new Date(sinceDate.getTime() + 60000);
		loadData(date1, date2, null, null, null, null, null, null, null, null,
				null, null, null, null);

		Event[] events = repository.get(new Range<Date>(sinceDate, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, null), createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		notContains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithUntilDateFilter() {
		Date date1 = new Date();
		Date untilDate = new Date(date1.getTime() + 60000);
		Date date2 = new Date(untilDate.getTime() + 60000);
		loadData(date1, date2, null, null, null, null, null, null, null, null,
				null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, untilDate),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, null), createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		contains(events, event1);
		notContains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMinimumLatitudeFilter() {
		float value1 = 1.0f;
		float minimumValue = 2.0f;
		float value2 = 3.0f;
		loadData(null, null, value1, value2, null, null, null, null, null,
				null, null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(minimumValue, null), new Range<Float>(null,
						null), new Range<Float>(null, null),
				createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		notContains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMaximumLatitudeFilter() {
		float value1 = 1.0f;
		float maximumValue = 2.0f;
		float value2 = 3.0f;
		loadData(null, null, value1, value2, null, null, null, null, null,
				null, null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, maximumValue), new Range<Float>(null,
						null), new Range<Float>(null, null),
				createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		contains(events, event1);
		notContains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMinimumLongitudeFilter() {
		float value1 = 1.0f;
		float minimumValue = 2.0f;
		float value2 = 3.0f;
		loadData(null, null, null, null, value1, value2, null, null, null,
				null, null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(minimumValue,
						null), new Range<Float>(null, null),
				createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		notContains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMaximumLongitudeFilter() {
		float value1 = 1.0f;
		float maximumValue = 2.0f;
		float value2 = 3.0f;
		loadData(null, null, null, null, value1, value2, null, null, null,
				null, null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null,
						maximumValue), new Range<Float>(null, null),
				createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		contains(events, event1);
		notContains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMinimumDepthFilter() {
		float value1 = 1.0f;
		float minimumValue = 2.0f;
		float value2 = 3.0f;
		loadData(null, null, null, null, null, null, value1, value2, null,
				null, null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(minimumValue, null),
				createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		notContains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMaximumDepthFilter() {
		float value1 = 1.0f;
		float maximumValue = 2.0f;
		float value2 = 3.0f;
		loadData(null, null, null, null, null, null, value1, value2, null,
				null, null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, maximumValue),
				createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		contains(events, event1);
		notContains(events, event2);
	}

	@Test
	@Transactional
	public void getWithDepthRangeFilter() {
		float value1 = 1.0f;
		float minimumValue = 2.0f;
		float value2 = 3.0f;
		float maximumValue = 4.0f;
		loadData(null, null, null, null, null, null, value1, value2, null,
				null, null, null, null, null);

		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(minimumValue, maximumValue),
				createMagnitudeRangesMap());
		Assert.assertEquals(1, events.length);
		notContains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMagnitudeOfOneTypeFilter() {
		MagnitudeType type1 = MagnitudeType.MB;
		float magnitude1 = 1.0f;
		MagnitudeType type2a = MagnitudeType.MB;
		float magnitude2a = 3.0f;
		MagnitudeType type2b = MagnitudeType.MC;
		float magnitude2b = 1.0f;
		loadData(null, null, null, null, null, null, null, null, type1,
				magnitude1, type2a, magnitude2a, type2b, magnitude2b);

		Map<MagnitudeType, Range<Float>> magnitudeRanges = new EnumMap<MagnitudeType, Range<Float>>(
				MagnitudeType.class);
		magnitudeRanges.put(MagnitudeType.MC, new Range<Float>(null, null));
		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, null), magnitudeRanges);
		Assert.assertEquals(1, events.length);
		notContains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMinimumMagnitudeOfOneTypeFilter() {
		MagnitudeType type1 = MagnitudeType.MB;
		float magnitude1 = 1.0f;
		float minimumValue = 2.0f;
		MagnitudeType type2a = MagnitudeType.MB;
		float magnitude2a = 3.0f;
		MagnitudeType type2b = MagnitudeType.MC;
		float magnitude2b = 1.0f;
		loadData(null, null, null, null, null, null, null, null, type1,
				magnitude1, type2a, magnitude2a, type2b, magnitude2b);

		Map<MagnitudeType, Range<Float>> magnitudeRanges = new EnumMap<MagnitudeType, Range<Float>>(
				MagnitudeType.class);
		magnitudeRanges.put(MagnitudeType.MB, new Range<Float>(minimumValue,
				null));
		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, null), magnitudeRanges);
		Assert.assertEquals(1, events.length);
		notContains(events, event1);
		contains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMaximumMagnitudeOfOneTypeFilter() {
		MagnitudeType type1 = MagnitudeType.MB;
		float magnitude1 = 1.0f;
		float maximumValue = 2.0f;
		MagnitudeType type2a = MagnitudeType.MB;
		float magnitude2a = 3.0f;
		MagnitudeType type2b = MagnitudeType.MC;
		float magnitude2b = 1.0f;
		loadData(null, null, null, null, null, null, null, null, type1,
				magnitude1, type2a, magnitude2a, type2b, magnitude2b);

		Map<MagnitudeType, Range<Float>> magnitudeRanges = new EnumMap<MagnitudeType, Range<Float>>(
				MagnitudeType.class);
		magnitudeRanges.put(MagnitudeType.MB, new Range<Float>(null,
				maximumValue));
		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, null), magnitudeRanges);
		Assert.assertEquals(1, events.length);
		contains(events, event1);
		notContains(events, event2);
	}

	@Test
	@Transactional
	public void getWithMaximumMagnitudeOfOneTypeOrAnotherTypeFilter() {
		MagnitudeType type1 = MagnitudeType.MB;
		float magnitude1 = 1.0f;
		float maximumValue = 2.0f;
		MagnitudeType type2a = MagnitudeType.MB;
		float magnitude2a = 3.0f;
		MagnitudeType type2b = MagnitudeType.MC;
		float magnitude2b = 1.0f;
		loadData(null, null, null, null, null, null, null, null, type1,
				magnitude1, type2a, magnitude2a, type2b, magnitude2b);

		Map<MagnitudeType, Range<Float>> magnitudeRanges = new EnumMap<MagnitudeType, Range<Float>>(
				MagnitudeType.class);
		magnitudeRanges.put(MagnitudeType.MB, new Range<Float>(null,
				maximumValue));
		magnitudeRanges.put(MagnitudeType.MC, new Range<Float>(null, null));
		Event[] events = repository.get(new Range<Date>(null, null),
				new Range<Float>(null, null), new Range<Float>(null, null),
				new Range<Float>(null, null), magnitudeRanges);
		Assert.assertEquals(2, events.length);
		contains(events, event1);
		contains(events, event2);
	}
}

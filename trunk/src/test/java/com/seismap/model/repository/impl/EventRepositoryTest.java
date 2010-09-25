package com.seismap.model.repository.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

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

public class EventRepositoryTest extends BaseIntegrationTest {

	@Autowired
	private EventRepository repository;

	@Autowired
	private HibernateTemplate hibernateTemplate;

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
}

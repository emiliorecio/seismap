package com.seismap.model.repository.impl;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.seismap.model.entity.Event;
import com.seismap.model.repository.EventRepository;

public class EventRepositoryImpl extends HibernateDaoSupport implements
		EventRepository {

	public void put(Event event) {
		this.getHibernateTemplate().save(event);
	}

}

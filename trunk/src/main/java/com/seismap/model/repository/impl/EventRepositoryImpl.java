package com.seismap.model.repository.impl;

import com.seismap.model.entity.Event;
import com.seismap.model.repository.EventRepository;

public class EventRepositoryImpl extends RepositoryImpl<Event, Long> implements
		EventRepository {

	public EventRepositoryImpl() {
		super(Event.class);
		
	}
}

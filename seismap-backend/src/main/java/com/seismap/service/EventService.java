package com.seismap.service;

import com.seismap.model.entity.Event;
import com.seismap.model.entity.DataBounds;
import com.seismap.model.entity.MagnitudeLimits;
import com.seismap.repository.EventRepository;
import com.seismap.repository.DataBoundsRepository;
import com.seismap.repository.MagnitudeLimitsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DataBoundsRepository dataBoundsRepository;
    private final MagnitudeLimitsRepository magnitudeLimitsRepository;

    public EventService(EventRepository eventRepository,
            DataBoundsRepository dataBoundsRepository,
            MagnitudeLimitsRepository magnitudeLimitsRepository) {
        this.eventRepository = eventRepository;
        this.dataBoundsRepository = dataBoundsRepository;
        this.magnitudeLimitsRepository = magnitudeLimitsRepository;
    }

    public Event getById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found: " + id));
    }

    @Transactional
    public Event update(Long id, Event updated) {
        Event event = getById(id);
        event.setName(updated.getName());
        event.setNotes(updated.getNotes());
        event.setReference(updated.getReference());
        event.setPerceivedDistance(updated.getPerceivedDistance());
        event.setDamagedDistance(updated.getDamagedDistance());
        return eventRepository.save(event);
    }

    public DataBounds getDataBounds() {
        return dataBoundsRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("DataBounds not found"));
    }

    public List<MagnitudeLimits> getMagnitudeLimits() {
        return magnitudeLimitsRepository.findAll();
    }
}

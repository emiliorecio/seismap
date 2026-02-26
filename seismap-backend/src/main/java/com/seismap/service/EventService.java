package com.seismap.service;

import com.seismap.dto.EventSummaryDto;
import com.seismap.dto.PolygonQueryRequest;
import com.seismap.model.entity.Event;
import com.seismap.model.entity.DataBounds;
import com.seismap.model.entity.MagnitudeLimits;
import com.seismap.repository.EventRepository;
import com.seismap.repository.DataBoundsRepository;
import com.seismap.repository.MagnitudeLimitsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DataBoundsRepository dataBoundsRepository;
    private final MagnitudeLimitsRepository magnitudeLimitsRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @SuppressWarnings("unchecked")
    public List<EventSummaryDto> findWithinPolygon(PolygonQueryRequest request) {
        StringBuilder sql = new StringBuilder(
                "SELECT id, date, depth, ST_Y(location) as lat, ST_X(location) as lon, " +
                        "name, reference, rankmagnitude " +
                        "FROM eventandaveragemagnitudes " +
                        "WHERE ST_Within(location, ST_GeomFromText(:wkt, 900913)) ");

        if (request.getMinDate() != null)
            sql.append("AND date >= :minDate ");
        if (request.getMaxDate() != null)
            sql.append("AND date <= :maxDate ");
        if (request.getMinDepth() != null)
            sql.append("AND depth >= :minDepth ");
        if (request.getMaxDepth() != null)
            sql.append("AND depth <= :maxDepth ");
        if (request.getMinMagnitude() != null)
            sql.append("AND rankmagnitude >= :minMagnitude ");
        if (request.getMaxMagnitude() != null)
            sql.append("AND rankmagnitude <= :maxMagnitude ");

        // Sort by date descending
        sql.append("ORDER BY date DESC LIMIT 500");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("wkt", request.getWkt());

        if (request.getMinDate() != null)
            query.setParameter("minDate", request.getMinDate());
        if (request.getMaxDate() != null)
            query.setParameter("maxDate", request.getMaxDate());
        if (request.getMinDepth() != null)
            query.setParameter("minDepth", request.getMinDepth());
        if (request.getMaxDepth() != null)
            query.setParameter("maxDepth", request.getMaxDepth());
        if (request.getMinMagnitude() != null)
            query.setParameter("minMagnitude", request.getMinMagnitude());
        if (request.getMaxMagnitude() != null)
            query.setParameter("maxMagnitude", request.getMaxMagnitude());

        List<Object[]> results = query.getResultList();

        return results.stream().map(row -> new EventSummaryDto(
                ((Number) row[0]).longValue(),
                ((java.sql.Timestamp) row[1]).toLocalDateTime(),
                ((Number) row[2]).floatValue(),
                ((Number) row[3]).doubleValue(),
                ((Number) row[4]).doubleValue(),
                (String) row[5],
                (String) row[6],
                row[7] != null ? ((Number) row[7]).floatValue() : null)).toList();
    }
}

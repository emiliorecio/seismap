package com.seismap.model.repository.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.seismap.model.entity.Event;
import com.seismap.model.entity.MagnitudeType;
import com.seismap.model.repository.EventRepository;
import com.seismap.model.repository.Range;

public class EventRepositoryImpl extends HibernateDaoSupport implements
		EventRepository {

	public void put(Event event) {
		this.getHibernateTemplate().save(event);
	}

	public Event[] get(Range<Date> dateRange, Range<Float> latitudeRange,
			Range<Float> longitudeRange, Range<Float> depthRange,
			Map<MagnitudeType, Range<Float>> magnitudeRanges) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Event.class);
		// date filter
		if (dateRange.getMinimum() != null) {
			criteria.add(Restrictions.ge("date", dateRange.getMinimum()));
		}
		if (dateRange.getMaximum() != null) {
			criteria.add(Restrictions.le("date", dateRange.getMaximum()));
		}
		// latitude filter
		if (latitudeRange.getMinimum() != null) {
			criteria.add(Restrictions.ge("latitude", latitudeRange.getMinimum()));
		}
		if (latitudeRange.getMaximum() != null) {
			criteria.add(Restrictions.le("latitude", latitudeRange.getMaximum()));
		}
		// longitude filter
		if (longitudeRange.getMinimum() != null) {
			criteria.add(Restrictions.ge("longitude",
					longitudeRange.getMinimum()));
		}
		if (longitudeRange.getMaximum() != null) {
			criteria.add(Restrictions.le("longitude",
					longitudeRange.getMaximum()));
		}
		// depth filter
		if (depthRange.getMinimum() != null) {
			criteria.add(Restrictions.ge("depth", depthRange.getMinimum()));
		}
		if (depthRange.getMaximum() != null) {
			criteria.add(Restrictions.le("depth", depthRange.getMaximum()));
		}
		// magnitude filter
		DetachedCriteria magnitudeCriteria = criteria
				.createCriteria("magnitudes");
		criteria.setFetchMode("magnitudes", FetchMode.JOIN);
		magnitudeCriteria.setFetchMode("reportingAgency", FetchMode.JOIN);

		// min
		Criterion magnitudeRangeCriteria = null;
		for (Entry<MagnitudeType, Range<Float>> magnitudeRange : magnitudeRanges
				.entrySet()) {
			MagnitudeType type = magnitudeRange.getKey();
			Range<Float> range = magnitudeRange.getValue();
			Criterion criterion = Restrictions.eq("type", type);
			if (range.getMinimum() != null) {
				criterion = Restrictions.and(criterion,
						Restrictions.ge("value", range.getMinimum()));
			}
			if (range.getMaximum() != null) {
				criterion = Restrictions.and(criterion,
						Restrictions.le("value", range.getMaximum()));
			}

			if (magnitudeRangeCriteria == null) {
				magnitudeRangeCriteria = criterion;
			} else {
				magnitudeRangeCriteria = Restrictions.or(
						magnitudeRangeCriteria, criterion);
			}
		}
		if (magnitudeRangeCriteria != null) {
			magnitudeCriteria.add(magnitudeRangeCriteria);
		}
		// fetch hint
		criteria.setFetchMode("magnitudes", FetchMode.JOIN);
		magnitudeCriteria.setFetchMode("reportingAgency", FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<?> events = getHibernateTemplate().findByCriteria(criteria);
		return events.toArray(new Event[events.size()]);
	}
}

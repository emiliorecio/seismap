package com.seismap.model.repository.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.seismap.model.entity.EventAndAverageMagnitude;
import com.seismap.model.entity.EventIdAndMagnitudeTypePk;
import com.seismap.model.repository.EventAndAverageMagnitudeRepository;
import com.seismap.service.common.RangeDto;
import com.seismap.service.event.MagnitudeType;

public class EventAndAverageMagnitudeRepositoryImpl extends
		RepositoryImpl<EventAndAverageMagnitude, EventIdAndMagnitudeTypePk> implements
		EventAndAverageMagnitudeRepository {

	public EventAndAverageMagnitudeRepositoryImpl() {
		super(EventAndAverageMagnitude.class);
	}

	public List<EventAndAverageMagnitude> find(RangeDto<Date> dateRangeDto,
			RangeDto<Float> latitudeRangeDto,
			RangeDto<Float> longitudeRangeDto, RangeDto<Float> depthRangeDto,
			MagnitudeType magnitudeType, RangeDto<Float> magnitudeRangeDto,
			boolean listUnmeasured) {
		DetachedCriteria criteria = getCriteria();
		// date filter
		if (dateRangeDto != null) {
			if (dateRangeDto.getMinimum() != null) {
				criteria.add(Restrictions.ge("eventDate",
						dateRangeDto.getMinimum()));
			}
			if (dateRangeDto.getMaximum() != null) {
				criteria.add(Restrictions.le("eventDate",
						dateRangeDto.getMaximum()));
			}
		}
		if (latitudeRangeDto != null) {
			// latitude filter
			if (latitudeRangeDto.getMinimum() != null) {
				criteria.add(Restrictions.ge("eventLatitude",
						latitudeRangeDto.getMinimum()));
			}
			if (latitudeRangeDto.getMaximum() != null) {
				criteria.add(Restrictions.le("eventLatitude",
						latitudeRangeDto.getMaximum()));
			}
		}
		// longitude filter
		if (longitudeRangeDto != null) {
			if (longitudeRangeDto.getMinimum() != null
					&& longitudeRangeDto.getMaximum() != null) {
				if (longitudeRangeDto.getMinimum().floatValue() <= longitudeRangeDto
						.getMaximum().floatValue()) {
					criteria.add(Restrictions.ge("eventLongitude",
							longitudeRangeDto.getMinimum()));
					criteria.add(Restrictions.le("eventLongitude",
							longitudeRangeDto.getMaximum()));
				} else {
					criteria.add(Restrictions.or(
							Restrictions.ge("eventLongitude",
									longitudeRangeDto.getMinimum()),
							Restrictions.le("eventLongitude",
									longitudeRangeDto.getMaximum())));
				}
			} else if (longitudeRangeDto.getMinimum() != null
					&& longitudeRangeDto.getMaximum() == null) {
				criteria.add(Restrictions.ge("eventLongitude",
						longitudeRangeDto.getMinimum()));
			} else if (longitudeRangeDto.getMinimum() == null
					&& longitudeRangeDto.getMaximum() != null) {
				criteria.add(Restrictions.le("eventLongitude",
						longitudeRangeDto.getMaximum()));
			}
		}
		// depth filter
		if (depthRangeDto != null) {
			if (depthRangeDto.getMinimum() != null) {
				criteria.add(Restrictions.ge("eventDepth",
						depthRangeDto.getMinimum()));
			}
			if (depthRangeDto.getMaximum() != null) {
				criteria.add(Restrictions.le("eventDepth",
						depthRangeDto.getMaximum()));
			}
		}
		// magnitude filter
		if (magnitudeRangeDto != null) {
			if (listUnmeasured) {
				if (magnitudeRangeDto.getMinimum() != null) {
					criteria.add(Restrictions.or(Restrictions
							.isNull("magnitudeAverageValue"), Restrictions.ge(
							"magnitudeAverageValue",
							magnitudeRangeDto.getMinimum())));
				}
				if (magnitudeRangeDto.getMaximum() != null) {
					criteria.add(Restrictions.or(Restrictions
							.isNull("magnitudeAverageValue"), Restrictions.le(
							"magnitudeAverageValue",
							magnitudeRangeDto.getMaximum())));
				}
			} else {
				if (magnitudeRangeDto.getMinimum() != null) {
					criteria.add(Restrictions.and(Restrictions
							.isNotNull("magnitudeAverageValue"), Restrictions
							.ge("magnitudeAverageValue",
									magnitudeRangeDto.getMinimum())));
				}
				if (magnitudeRangeDto.getMaximum() != null) {
					criteria.add(Restrictions.and(Restrictions
							.isNotNull("magnitudeAverageValue"), Restrictions
							.le("magnitudeAverageValue",
									magnitudeRangeDto.getMaximum())));
				}
			}
		}

		// fetch hint
		return getListByCriteria(criteria, true);
	}
}

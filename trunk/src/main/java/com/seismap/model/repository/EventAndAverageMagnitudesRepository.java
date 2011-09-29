package com.seismap.model.repository;

import java.util.Date;
import java.util.List;

import com.seismap.model.entity.EventAndAverageMagnitudes;
import com.seismap.service.common.RangeDto;
import com.seismap.service.event.MagnitudeType;

public interface EventAndAverageMagnitudesRepository extends
		ReadOnlyRepository<EventAndAverageMagnitudes, Long> {

	List<EventAndAverageMagnitudes> find(RangeDto<Date> dateRangeDto,
			RangeDto<Float> latitudeRangeDto,
			RangeDto<Float> longitudeRangeDto, RangeDto<Float> depthRangeDto,
			MagnitudeType magnitudeType, RangeDto<Float> magnitudeRangeDto,
			boolean listUnmeasured);

}

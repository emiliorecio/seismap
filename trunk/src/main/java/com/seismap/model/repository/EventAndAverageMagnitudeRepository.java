package com.seismap.model.repository;

import java.util.Date;
import java.util.List;

import com.seismap.model.entity.EventAndAverageMagnitude;
import com.seismap.model.entity.EventIdAndMagnitudeTypePk;
import com.seismap.service.common.RangeDto;
import com.seismap.service.event.MagnitudeType;

public interface EventAndAverageMagnitudeRepository extends
		Repository<EventAndAverageMagnitude, EventIdAndMagnitudeTypePk> {

	List<EventAndAverageMagnitude> find(RangeDto<Date> dateRangeDto,
			RangeDto<Float> latitudeRangeDto,
			RangeDto<Float> longitudeRangeDto, RangeDto<Float> depthRangeDto,
			MagnitudeType magnitudeType, RangeDto<Float> magnitudeRangeDto,
			boolean listUnmeasured);

}

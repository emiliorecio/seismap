package com.seismap.model.repository;

import com.seismap.model.entity.MagnitudeLimits;
import com.seismap.service.event.MagnitudeType;

public interface MagnitudeLimitsRepository extends
		ReadOnlyRepository<MagnitudeLimits, MagnitudeType> {
}

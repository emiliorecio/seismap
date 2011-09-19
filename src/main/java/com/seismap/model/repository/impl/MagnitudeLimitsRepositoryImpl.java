package com.seismap.model.repository.impl;

import com.seismap.model.entity.MagnitudeLimits;
import com.seismap.model.repository.MagnitudeLimitsRepository;
import com.seismap.service.event.MagnitudeType;

public class MagnitudeLimitsRepositoryImpl extends
		RepositoryImpl<MagnitudeLimits, MagnitudeType> implements
		MagnitudeLimitsRepository {

	public MagnitudeLimitsRepositoryImpl() {
		super(MagnitudeLimits.class);
	}

}

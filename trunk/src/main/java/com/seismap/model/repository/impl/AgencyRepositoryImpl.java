package com.seismap.model.repository.impl;

import com.seismap.model.entity.Agency;
import com.seismap.model.repository.AgencyRepository;

public class AgencyRepositoryImpl extends RepositoryImpl<Agency, Long>
		implements AgencyRepository {

	public AgencyRepositoryImpl() {
		super(Agency.class);
	}

	public Agency getByCode(String code) {
		return getByValue("code", code);
	}

}

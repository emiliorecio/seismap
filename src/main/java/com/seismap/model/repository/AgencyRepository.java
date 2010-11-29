package com.seismap.model.repository;

import com.seismap.model.entity.Agency;

public interface AgencyRepository {

	void put(Agency agency);
	
	Agency getByCode(String code);
}

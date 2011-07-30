package com.seismap.model.repository;

import com.seismap.model.entity.Agency;

public interface AgencyRepository extends Repository<Agency, Long> {

	Agency getByCode(String code);
}

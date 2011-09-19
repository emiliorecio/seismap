package com.seismap.model.repository;

import com.seismap.model.entity.Application;

public interface ApplicationRepository extends
		ReadOnlyRepository<Application, Long> {

	Application fetch();
}

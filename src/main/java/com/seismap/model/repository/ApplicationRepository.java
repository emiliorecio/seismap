package com.seismap.model.repository;

import com.seismap.model.entity.Application;

public interface ApplicationRepository extends Repository<Application, Long> {

	Application get();
}

package com.seismap.model.repository.impl;

import com.seismap.model.entity.Application;
import com.seismap.model.repository.ApplicationRepository;

public class ApplicationRepositoryImpl extends
		RepositoryImpl<Application, Long> implements ApplicationRepository {

	public ApplicationRepositoryImpl() {
		super(Application.class);
	}

	public Application fetch() {
		return fetchSingleton();
	}
}

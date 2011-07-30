package com.seismap.model.repository.impl;

import com.seismap.model.entity.Application;
import com.seismap.model.repository.ApplicationRepository;

public class ApplicationRepositoryImpl extends
		RepositoryImpl<Application, Long> implements ApplicationRepository {

	public ApplicationRepositoryImpl() {
		super(Application.class);
	}

	public Application get() {
		Application application = getByCriteria(getCriteria(), true);
		if (application == null) {
			application = new Application();
			put(application);
		}
		return application;
	}
}

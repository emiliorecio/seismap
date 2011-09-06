package com.seismap.model.repository.impl;

import com.seismap.model.entity.DataBounds;
import com.seismap.model.repository.DataBoundsRepository;

public class DataBoundsRepositoryImpl extends RepositoryImpl<DataBounds, Long>
		implements DataBoundsRepository {

	public DataBoundsRepositoryImpl() {
		super(DataBounds.class);
	}

	public DataBounds get() {
		DataBounds dataBounds = fetchByCriteria(getCriteria(), true);
		return dataBounds;
	}
}

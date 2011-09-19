package com.seismap.model.repository;

import com.seismap.model.entity.DataBounds;

public interface DataBoundsRepository extends
		ReadOnlyRepository<DataBounds, Long> {

	DataBounds fetch();
}

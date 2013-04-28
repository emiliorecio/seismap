package com.seismap.model.repository;

import java.io.Serializable;

public interface Repository<T, K extends Serializable> extends
		ReadOnlyRepository<T, K> {

	void put(T object);
	
	void delete(T entity);

}

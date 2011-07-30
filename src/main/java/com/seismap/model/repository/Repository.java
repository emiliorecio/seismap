package com.seismap.model.repository;

import java.io.Serializable;

public interface Repository<T, K extends Serializable> {

	T get(K id);

	T fetch(K id);

	void put(T object);

}

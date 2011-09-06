package com.seismap.model.repository;

import java.io.Serializable;

public interface ReadOnlyRepository<T, K extends Serializable> {

	T get(K id);

	T fetch(K id);

}

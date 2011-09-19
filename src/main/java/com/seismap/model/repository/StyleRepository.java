package com.seismap.model.repository;

import com.seismap.model.entity.Style;

public interface StyleRepository extends Repository<Style, Long> {

	Style getByName(String name);

}

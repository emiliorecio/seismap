package com.seismap.model.repository;

import com.seismap.model.entity.Category;
import com.seismap.model.entity.Map;
import com.seismap.model.entity.User;

public interface MapRepository extends Repository<Map, Long> {

	Map getByCategoryAndName(Category category, String mapName);

	Map getByUserAndName(User user, String mapName);

}

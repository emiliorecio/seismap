package com.seismap.model.repository;

import com.seismap.model.entity.Category;

public interface CategoryRepository extends Repository<Category, Long> {

	Category getByName(String name);

}

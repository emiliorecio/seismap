package com.seismap.model.repository.impl;

import com.seismap.model.entity.Category;
import com.seismap.model.repository.CategoryRepository;

public class CategoryRepositoryImpl extends RepositoryImpl<Category, Long>
		implements CategoryRepository {

	public CategoryRepositoryImpl() {
		super(Category.class);

	}

	public Category getByName(String name) {
		return getByValue("name", name);
	}
}

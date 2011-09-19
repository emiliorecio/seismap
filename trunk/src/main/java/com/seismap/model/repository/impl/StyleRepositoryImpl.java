package com.seismap.model.repository.impl;

import com.seismap.model.entity.Style;
import com.seismap.model.repository.StyleRepository;

public class StyleRepositoryImpl extends RepositoryImpl<Style, Long> implements
		StyleRepository {

	public StyleRepositoryImpl() {
		super(Style.class);

	}

	public Style getByName(String name) {
		return getByValue("name", name);
	}
}

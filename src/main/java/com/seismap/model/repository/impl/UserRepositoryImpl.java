package com.seismap.model.repository.impl;

import com.seismap.model.entity.User;
import com.seismap.model.repository.UserRepository;

public class UserRepositoryImpl extends RepositoryImpl<User, Long> implements
		UserRepository {

	public UserRepositoryImpl() {
		super(User.class);

	}

	public User getByEmail(String email) {
		return getByValue("email", email);
	}
}

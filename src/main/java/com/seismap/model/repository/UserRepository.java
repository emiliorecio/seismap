package com.seismap.model.repository;

import com.seismap.model.entity.User;

public interface UserRepository extends Repository<User, Long> {

	User getByEmail(String email);

}

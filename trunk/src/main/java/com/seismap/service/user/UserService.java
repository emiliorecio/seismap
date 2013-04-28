package com.seismap.service.user;

import com.seismap.service.common.ActorCredentialsDto;

public interface UserService {

	CreateUserResponseDto create(ActorCredentialsDto actorCredentials, CreateUserRequestDto request);

	ValidateUserResponseDto validate(ActorCredentialsDto actorCredentials, ValidateUserRequestDto request);

}

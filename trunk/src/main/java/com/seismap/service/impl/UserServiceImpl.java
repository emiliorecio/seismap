package com.seismap.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.User;
import com.seismap.model.repository.UserRepository;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;
import com.seismap.service.user.CreateUserRequestDto;
import com.seismap.service.user.CreateUserResponseDto;
import com.seismap.service.user.UserDto;
import com.seismap.service.user.UserService;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	protected UserServiceImpl() {
	}

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public CreateUserResponseDto create(ActorCredentialsDto actorCredentials, CreateUserRequestDto request) {
		String email = request.getUserEmail();
		User existingUser = userRepository.getByEmail(email);
		if (existingUser != null) {
			CreateUserResponseDto exceptionResponse = new CreateUserResponseDto(
					ExceptionCause.DUPLICATE_CATEGORY_NAME, "El usuario '"
							+ email + "' ya existe.");
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.USER_EMAIL, email);
			return exceptionResponse;
		}
		String name = request.getUserName();
		String password = request.getUserPassword();
		User user = new User(name, email, password);
		UserDto userDto = DtoMarshaler.unmarshallUser(user);
		CreateUserResponseDto response = new CreateUserResponseDto(userDto);
		return response;
	}

	// @Transactional
	// public EventsAndAverageMagnitudesFindResponseDto
	// get(EventsAndAverageMagnitudesFindRequestDto request) {
	// return new
	// EventsAndAverageMagnitudesFindResponseDto(DtoMarshaler.unmarshall(mapRepository
	// .get(DtoMarshaler.marshall(request.getDateRange()),
	// DtoMarshaler.marshall(request.getLatitudeRange()),
	// DtoMarshaler.marshall(request.getLongitudeRange()),
	// DtoMarshaler.marshall(request.getDepthRange()),
	// DtoMarshaler.marshallMagnitudeRange(request
	// .getMagnitudeRanges()))));
	// }

}

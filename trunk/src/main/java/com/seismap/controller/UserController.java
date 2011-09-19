package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.user.CreateUserRequestDto;
import com.seismap.service.user.CreateUserResponseDto;
import com.seismap.service.user.UserService;

@Controller
@RequestMapping("action/user")
public class UserController extends SeismapController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public CreateUserResponseDto create(
			@RequestBody CreateUserRequestDto request) {
		return userService.create(getActorCredentials(), request);
	}
}

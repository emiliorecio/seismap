package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.application.ApplicationService;
import com.seismap.service.application.GetApplicationRequestDto;
import com.seismap.service.application.GetApplicationResponseDto;
import com.seismap.service.application.GetApplicationSettingsRequestDto;
import com.seismap.service.application.GetApplicationSettingsResponseDto;

@Controller
@RequestMapping("action/application")
public class ApplicationController extends SeismapController {

	private ApplicationService applicationService;

	public ApplicationController(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public GetApplicationResponseDto get(
			@RequestBody GetApplicationRequestDto request) {
		return applicationService.get(getActorCredentials(), request);
	}

	@RequestMapping(value = "getSettings", method = RequestMethod.POST)
	@ResponseBody
	public GetApplicationSettingsResponseDto getSettings(
			@RequestBody GetApplicationSettingsRequestDto request) {
		return applicationService.getSettings(getActorCredentials(), request);
	}
}

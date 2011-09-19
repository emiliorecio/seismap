package com.seismap.controller;

import com.seismap.service.common.ActorCredentialsDto;

public class SeismapController {

	protected ActorCredentialsDto getActorCredentials() {
		return new ActorCredentialsDto(Long.valueOf(1L), "");
	}
}

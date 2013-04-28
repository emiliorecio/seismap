package com.seismap.service.application;

import com.seismap.service.common.ActorCredentialsDto;

public interface ApplicationService {

	GetApplicationResponseDto get(ActorCredentialsDto actorCredentials,
			GetApplicationRequestDto request);

	GetApplicationSettingsResponseDto getSettings(
			ActorCredentialsDto actorCredentials,
			GetApplicationSettingsRequestDto request);

}

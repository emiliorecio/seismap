package com.seismap.service.style;

import com.seismap.service.common.ActorCredentialsDto;

public interface StyleService {

	ListStylesResponseDto list(ActorCredentialsDto actorCredentials,
			ListStylesRequestDto request);

	CreateStyleResponseDto create(ActorCredentialsDto actorCredentials,
			CreateStyleRequestDto request);
}

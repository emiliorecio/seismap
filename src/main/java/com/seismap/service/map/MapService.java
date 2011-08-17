package com.seismap.service.map;

import com.seismap.service.common.ActorCredentialsDto;

public interface MapService {

	CreateMapResponseDto create(ActorCredentialsDto actorCredentials,
			CreateMapRequestDto request);

	GetMapResponseDto get(ActorCredentialsDto actorCredentials,
			GetMapRequestDto request);

	ListUserMapsResponseDto listByUser(ActorCredentialsDto actorCredentials,
			ListUserMapsRequestDto request);

	GetLayerServerUriResponseDto getLayerServerUri(
			ActorCredentialsDto actorCredentials,
			GetLayerServerUriRequestDto request);

}

package com.seismap.service.map;

import com.seismap.service.common.ActorCredentialsDto;

public interface MapService {

	GetDefaultMapResponseDto getDefault(
			ActorCredentialsDto actorCredentials,
			GetDefaultMapRequestDto request);

	CreateMapResponseDto create(ActorCredentialsDto actorCredentials,
			CreateMapRequestDto request);

	RenameMapResponseDto rename(
			ActorCredentialsDto actorCredentials,
			RenameMapRequestDto request);

	GetMapResponseDto get(ActorCredentialsDto actorCredentials,
			GetMapRequestDto request);

	ListUserMapsResponseDto listByUser(ActorCredentialsDto actorCredentials,
			ListUserMapsRequestDto request);

	GetLayerServerUriResponseDto getLayerServerUri(
			ActorCredentialsDto actorCredentials,
			GetLayerServerUriRequestDto request);

	ModifyMapResponseDto modify(ActorCredentialsDto actorCredentials,
			ModifyMapRequestDto request);
}

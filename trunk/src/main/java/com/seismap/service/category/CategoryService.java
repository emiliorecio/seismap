package com.seismap.service.category;

import com.seismap.service.common.ActorCredentialsDto;

public interface CategoryService {

	ListCategoriesResponseDto list(ActorCredentialsDto actorCredentials, ListCategoriesRequestDto request);

	CreateCategoryResponseDto create(ActorCredentialsDto actorCredentials, CreateCategoryRequestDto request);
}

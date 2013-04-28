package com.seismap.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.Category;
import com.seismap.model.repository.ApplicationRepository;
import com.seismap.model.repository.CategoryRepository;
import com.seismap.model.repository.UserRepository;
import com.seismap.service.category.CategoryDto;
import com.seismap.service.category.CategoryService;
import com.seismap.service.category.CreateCategoryRequestDto;
import com.seismap.service.category.CreateCategoryResponseDto;
import com.seismap.service.category.ListCategoriesRequestDto;
import com.seismap.service.category.ListCategoriesResponseDto;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;

public class CategoryServiceImpl extends AbstractServiceImpl implements
		CategoryService {

	private ApplicationRepository applicationRepository;

	private CategoryRepository categoryRepository;

	protected CategoryServiceImpl() {
	}

	public CategoryServiceImpl(ApplicationRepository applicationRepository,
			UserRepository userRepository, CategoryRepository categoryRepository) {
		super(userRepository);
		this.applicationRepository = applicationRepository;
		this.categoryRepository = categoryRepository;
	}

	@Transactional
	public ListCategoriesResponseDto list(ActorCredentialsDto actorCredentials,
			ListCategoriesRequestDto request) {
		try {
			validateUser(actorCredentials, Role.ANONYMOUS);
		} catch (UnauthorizedException e) {
			return new ListCategoriesResponseDto(ExceptionCause.UNAUTHORIZED,
					e.getMessage());
		}
		return new ListCategoriesResponseDto(
				DtoMarshaler.unmarshallCategories(applicationRepository
						.fetchSingleton().getCategories()));
	}

	@Transactional
	public CreateCategoryResponseDto create(
			ActorCredentialsDto actorCredentials,
			CreateCategoryRequestDto request) {
		try {
			validateUser(actorCredentials, Role.ADMIN);
		} catch (UnauthorizedException e) {
			return new CreateCategoryResponseDto(ExceptionCause.UNAUTHORIZED,
					e.getMessage());
		}
		String name = request.getCategoryName();
		Category existingCategory = categoryRepository.getByName(name);
		if (existingCategory != null) {
			CreateCategoryResponseDto exceptionResponse = new CreateCategoryResponseDto(
					ExceptionCause.DUPLICATE_CATEGORY_NAME, "La categoria '"
							+ name + "' ya existe.");
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.CATEGORY_NAME, name);
			return exceptionResponse;
		}
		Category category = new Category(name);
		applicationRepository.fetchSingleton().getCategories().add(category);
		categoryRepository.put(category);
		CategoryDto categoryDto = DtoMarshaler.unmarshallCategory(category);
		CreateCategoryResponseDto response = new CreateCategoryResponseDto(
				categoryDto);
		return response;
	}
}

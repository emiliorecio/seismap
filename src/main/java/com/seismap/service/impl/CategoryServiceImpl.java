package com.seismap.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.Category;
import com.seismap.model.repository.ApplicationRepository;
import com.seismap.model.repository.CategoryRepository;
import com.seismap.service.category.CategoryDto;
import com.seismap.service.category.CategoryService;
import com.seismap.service.category.CreateCategoryRequestDto;
import com.seismap.service.category.CreateCategoryResponseDto;
import com.seismap.service.category.ListCategoriesRequestDto;
import com.seismap.service.category.ListCategoriesResponseDto;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;

public class CategoryServiceImpl implements CategoryService {

	private ApplicationRepository applicationRepository;

	private CategoryRepository categoryRepository;

	protected CategoryServiceImpl() {
	}

	public CategoryServiceImpl(ApplicationRepository applicationRepository,
			CategoryRepository categoryRepository) {
		this.applicationRepository = applicationRepository;
		this.categoryRepository = categoryRepository;
	}

	@Transactional
	public ListCategoriesResponseDto list(ActorCredentialsDto actorCredentials,
			ListCategoriesRequestDto request) {
		return new ListCategoriesResponseDto(
				DtoMarshaler.unmarshallCategories(applicationRepository.fetch()
						.getCategories()));
	}

	@Transactional
	public CreateCategoryResponseDto create(
			ActorCredentialsDto actorCredentials,
			CreateCategoryRequestDto request) {
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
		applicationRepository.fetch().getCategories().add(category);
		categoryRepository.put(category);
		CategoryDto categoryDto = DtoMarshaler.unmarshallCategory(category);
		CreateCategoryResponseDto response = new CreateCategoryResponseDto(
				categoryDto);
		return response;
	}
}

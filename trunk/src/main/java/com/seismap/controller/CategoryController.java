package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.category.ListCategoriesRequestDto;
import com.seismap.service.category.ListCategoriesResponseDto;
import com.seismap.service.category.CategoryService;
import com.seismap.service.category.CreateCategoryRequestDto;
import com.seismap.service.category.CreateCategoryResponseDto;

@Controller
@RequestMapping("action/category")
public class CategoryController extends SeismapController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public CreateCategoryResponseDto create(
			@RequestBody CreateCategoryRequestDto request, Model model) {
		return categoryService.create(getActorCredentials(), request);
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public ListCategoriesResponseDto list(
			@RequestBody ListCategoriesRequestDto request, Model model) {
		return categoryService.list(getActorCredentials(), request);
	}
}

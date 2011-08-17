package com.seismap.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seismap.service.category.ListCategoriesRequestDto;
import com.seismap.service.category.ListCategoriesResponseDto;
import com.seismap.service.map.GetMapRequestDto;
import com.seismap.service.map.GetMapResponseDto;
import com.seismap.service.map.GetLayerServerUriRequestDto;
import com.seismap.service.map.GetLayerServerUriResponseDto;
import com.seismap.service.map.ListUserMapsRequestDto;
import com.seismap.service.map.ListUserMapsResponseDto;

@Controller
@RequestMapping("")
public class PageController extends SeismapController {

	private CategoryController categoryController;

	private MapController mapController;

	public PageController(CategoryController categoryController,
			MapController mapController) {
		this.categoryController = categoryController;
		this.mapController = mapController;
	}

	private String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			throw new IllegalArgumentException(
					"Error generating template string: " + e, e);
		} catch (JsonMappingException e) {
			throw new IllegalArgumentException(
					"Error generating template string: " + e, e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Error generating template string: " + e, e);
		}
	}

	private void loadGeneralData(Model model) {
		ListCategoriesResponseDto categoriesResponse = categoryController.list(
				new ListCategoriesRequestDto(), model);
		if (categoriesResponse.isException()) {
			throw new IllegalStateException(categoriesResponse.toString());
		}
		model.addAttribute("categories", categoriesResponse.getValue());
		ListUserMapsResponseDto mapsResponse = mapController.listByUser(
				new ListUserMapsRequestDto(), model);
		if (mapsResponse.isException()) {
			throw new IllegalStateException(mapsResponse.toString());
		}
		model.addAttribute("maps", mapsResponse.getValue());
	}

	@RequestMapping("")
	public String home(Model model) {
		loadGeneralData(model);
		return "home";
	}

	@RequestMapping("map/{mapId}")
	public String map(@PathVariable("mapId") Long mapId, Model model) {
		loadGeneralData(model);
		GetMapResponseDto mapResponse = mapController.get(new GetMapRequestDto(
				mapId), model);
		if (mapResponse.isException()) {
			throw new IllegalStateException(mapResponse.toString());
		}
		model.addAttribute("map", mapResponse.getValue());
		model.addAttribute("map_json", toJson(mapResponse.getValue()));

		GetLayerServerUriResponseDto layerServerUriResponse = mapController
				.getLayerServerUri(new GetLayerServerUriRequestDto(), model);
		if (layerServerUriResponse.isException()) {
			throw new IllegalStateException(layerServerUriResponse.toString());
		}
		model.addAttribute("layerServerUri", layerServerUriResponse.getValue());
		return "map";
	}
}

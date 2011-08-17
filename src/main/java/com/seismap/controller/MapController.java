package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.map.CreateMapRequestDto;
import com.seismap.service.map.CreateMapResponseDto;
import com.seismap.service.map.GetMapRequestDto;
import com.seismap.service.map.GetMapResponseDto;
import com.seismap.service.map.GetLayerServerUriRequestDto;
import com.seismap.service.map.GetLayerServerUriResponseDto;
import com.seismap.service.map.ListUserMapsRequestDto;
import com.seismap.service.map.ListUserMapsResponseDto;
import com.seismap.service.map.MapService;

@Controller
@RequestMapping("action/map")
public class MapController extends SeismapController {

	private MapService mapService;

	public MapController(MapService mapService) {
		this.mapService = mapService;
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public CreateMapResponseDto create(
			@RequestBody CreateMapRequestDto request, Model model) {
		return mapService.create(getActorCredentials(), request);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public GetMapResponseDto get(@RequestBody GetMapRequestDto request,
			Model model) {
		return mapService.get(getActorCredentials(), request);
	}

	@RequestMapping(value = "listByUser", method = RequestMethod.POST)
	@ResponseBody
	public ListUserMapsResponseDto listByUser(
			@RequestBody ListUserMapsRequestDto request, Model model) {
		return mapService.listByUser(getActorCredentials(), request);
	}

	@RequestMapping(value = "getTileServerUri", method = RequestMethod.POST)
	@ResponseBody
	public GetLayerServerUriResponseDto getLayerServerUri(
			@RequestBody GetLayerServerUriRequestDto request, Model model) {
		return mapService.getLayerServerUri(getActorCredentials(), request);
	}
}

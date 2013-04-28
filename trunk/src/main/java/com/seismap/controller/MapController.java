package com.seismap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.map.CreateMapRequestDto;
import com.seismap.service.map.CreateMapResponseDto;
import com.seismap.service.map.DeleteMapRequestDto;
import com.seismap.service.map.DeleteMapResponseDto;
import com.seismap.service.map.GetDefaultMapRequestDto;
import com.seismap.service.map.GetDefaultMapResponseDto;
import com.seismap.service.map.GetLegendRequestDto;
import com.seismap.service.map.GetLegendResponseDto;
import com.seismap.service.map.GetMapRequestDto;
import com.seismap.service.map.GetMapResponseDto;
import com.seismap.service.map.ListUserMapsRequestDto;
import com.seismap.service.map.ListUserMapsResponseDto;
import com.seismap.service.map.MapService;
import com.seismap.service.map.ModifyMapRequestDto;
import com.seismap.service.map.ModifyMapResponseDto;
import com.seismap.service.map.RenameMapRequestDto;
import com.seismap.service.map.RenameMapResponseDto;

@Controller
@RequestMapping("action/map")
public class MapController extends SeismapController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MapController.class);

	private MapService mapService;

	public MapController(MapService mapService) {
		this.mapService = mapService;
	}

	@RequestMapping(value = "getDefault", method = RequestMethod.POST)
	@ResponseBody
	public GetDefaultMapResponseDto getDefault(
			@RequestBody GetDefaultMapRequestDto request) {
		return mapService.getDefault(getActorCredentials(), request);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public CreateMapResponseDto create(@RequestBody CreateMapRequestDto request) {
		return mapService.create(getActorCredentials(), request);
	}

	@RequestMapping(value = "rename", method = RequestMethod.POST)
	@ResponseBody
	public RenameMapResponseDto rename(@RequestBody RenameMapRequestDto request) {
		return mapService.rename(getActorCredentials(), request);
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public DeleteMapResponseDto delete(@RequestBody DeleteMapRequestDto request) {
		return mapService.delete(getActorCredentials(), request);
	}

	@RequestMapping(value = "modify", method = RequestMethod.POST)
	@ResponseBody
	public ModifyMapResponseDto modify(@RequestBody ModifyMapRequestDto request) {
		return mapService.modify(getActorCredentials(), request);
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public GetMapResponseDto get(@RequestBody GetMapRequestDto request) {
		return mapService.get(getActorCredentials(), request);
	}

	@RequestMapping(value = "listByUser", method = RequestMethod.POST)
	@ResponseBody
	public ListUserMapsResponseDto listByUser(
			@RequestBody ListUserMapsRequestDto request) {
		return mapService.listByUser(getActorCredentials(), request);
	}

	@RequestMapping(value = "getLegend", method = RequestMethod.GET)
	public ResponseEntity<Resource> getLegend(
			@RequestParam("name") GetLegendRequestDto request) {
		GetLegendResponseDto response = mapService.getLegend(
				getActorCredentials(), request);
		if (response.isException()) {
			LOGGER.warn("action/map/getLegend error: "
					+ response.getException());
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		Resource resource = response.getValue();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
}

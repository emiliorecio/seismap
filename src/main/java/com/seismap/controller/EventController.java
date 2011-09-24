package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.event.EventService;
import com.seismap.service.event.GetDataBoundsRequestDto;
import com.seismap.service.event.GetDataBoundsResponseDto;
import com.seismap.service.event.GetEventRequestDto;
import com.seismap.service.event.GetEventResponseDto;
import com.seismap.service.event.GetMagnitudeLimitsRequestDto;
import com.seismap.service.event.GetMagnitudeLimitsResponseDto;

@Controller
@RequestMapping("action/event")
public class EventController extends SeismapController {

	private EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public GetEventResponseDto get(
			@RequestBody GetEventRequestDto request) {
		return eventService.get(getActorCredentials(), request);
	}

	@RequestMapping(value = "getDataBounds", method = RequestMethod.POST)
	@ResponseBody
	public GetDataBoundsResponseDto getDataBounds(
			@RequestBody GetDataBoundsRequestDto request) {
		return eventService.getDataBounds(getActorCredentials(), request);
	}

	@RequestMapping(value = "getMagnitudeLimits", method = RequestMethod.POST)
	@ResponseBody
	public GetMagnitudeLimitsResponseDto getMagnitudeLimits(
			@RequestBody GetMagnitudeLimitsRequestDto request) {
		return eventService.getMagnitudeLimits(getActorCredentials(), request);
	}
}

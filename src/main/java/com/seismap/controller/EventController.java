package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.event.EventService;
import com.seismap.service.event.GetDataBoundsRequestDto;
import com.seismap.service.event.GetDataBoundsResponseDto;

@Controller
@RequestMapping("action/event")
public class EventController extends SeismapController {

	private EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@RequestMapping(value = "getDataBounds", method = RequestMethod.POST)
	@ResponseBody
	public GetDataBoundsResponseDto getDataBounds(
			@RequestBody GetDataBoundsRequestDto request, Model model) {
		return eventService.getDataBounds(getActorCredentials(), request);
	}
}

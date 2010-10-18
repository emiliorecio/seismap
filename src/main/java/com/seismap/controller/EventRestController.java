package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.EventService;
import com.seismap.service.EventsGetRequestDto;
import com.seismap.service.EventsGetResponseDto;

@Controller
@RequestMapping("/rest/event")
public class EventRestController {

	private EventService eventService;

	public EventRestController(EventService eventService) {
		this.eventService = eventService;
	}

	@RequestMapping(method=RequestMethod.POST, value="/get")
	@ResponseBody
	public EventsGetResponseDto get(EventsGetRequestDto request) {
		return eventService.get(request);
	}
}

package com.seismap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seismap.service.style.CreateStyleRequestDto;
import com.seismap.service.style.CreateStyleResponseDto;
import com.seismap.service.style.ListStylesRequestDto;
import com.seismap.service.style.ListStylesResponseDto;
import com.seismap.service.style.StyleService;

@Controller
@RequestMapping("action/style")
public class StyleController extends SeismapController {

	private StyleService styleService;

	public StyleController(StyleService styleService) {
		this.styleService = styleService;
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public CreateStyleResponseDto create(
			@RequestBody CreateStyleRequestDto request) {
		return styleService.create(getActorCredentials(), request);
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public ListStylesResponseDto list(@RequestBody ListStylesRequestDto request) {
		return styleService.list(getActorCredentials(), request);
	}
}

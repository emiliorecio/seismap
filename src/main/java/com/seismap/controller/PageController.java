package com.seismap.controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seismap.service.application.GetApplicationSettingsRequestDto;
import com.seismap.service.application.GetApplicationSettingsResponseDto;
import com.seismap.service.category.ListCategoriesRequestDto;
import com.seismap.service.category.ListCategoriesResponseDto;
import com.seismap.service.event.GetDataBoundsRequestDto;
import com.seismap.service.event.GetDataBoundsResponseDto;
import com.seismap.service.event.GetMagnitudeLimitsRequestDto;
import com.seismap.service.event.GetMagnitudeLimitsResponseDto;
import com.seismap.service.map.*;
import com.seismap.service.style.ListStylesRequestDto;
import com.seismap.service.style.ListStylesResponseDto;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;

@Controller
@RequestMapping("")
public class PageController extends SeismapController {

	private ApplicationController applicationController;

	private CategoryController categoryController;

	private MapController mapController;

	private EventController eventController;

	private StyleController styleController;

	private ClientHttpRequestFactory httpRequestFactory;

	public PageController(ApplicationController applicationController,
			CategoryController categoryController, MapController mapController,
			EventController eventController, StyleController styleController,
			ClientHttpRequestFactory httpRequestFactory) {
		this.applicationController = applicationController;
		this.categoryController = categoryController;
		this.mapController = mapController;
		this.eventController = eventController;
		this.styleController = styleController;
		this.httpRequestFactory = httpRequestFactory;
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
		GetApplicationSettingsResponseDto applicationSettingsResponse = applicationController
				.getSettings(new GetApplicationSettingsRequestDto());
		if (applicationSettingsResponse.isException()) {
			throw new IllegalStateException(
					applicationSettingsResponse.toString());
		}
		model.addAttribute("applicationSettings",
				applicationSettingsResponse.getValue());
		model.addAttribute("applicationSettings_json",
				toJson(applicationSettingsResponse.getValue()));

		ListCategoriesResponseDto categoriesResponse = categoryController
				.list(new ListCategoriesRequestDto());
		if (categoriesResponse.isException()) {
			throw new IllegalStateException(categoriesResponse.toString());
		}
		model.addAttribute("categories", categoriesResponse.getValue());

		ListUserMapsResponseDto mapsResponse = mapController
				.listByUser(new ListUserMapsRequestDto(getActorCredentials()
						.getUserId()));
		if (mapsResponse.isException()) {
			throw new IllegalStateException(mapsResponse.toString());
		}
		model.addAttribute("maps", mapsResponse.getValue());
	}

	@RequestMapping("")
	public String index(Model model) {
		loadGeneralData(model);
		GetDefaultMapResponseDto mapResponse = mapController
				.getDefault(new GetDefaultMapRequestDto());
		if (mapResponse.isException()) {
			throw new IllegalStateException(mapResponse.toString());
		}
		model.addAttribute("map", mapResponse.getValue());
		model.addAttribute("map_json", toJson(mapResponse.getValue()));

		ListStylesResponseDto stylesResponse = styleController
				.list(new ListStylesRequestDto());
		if (stylesResponse.isException()) {
			throw new IllegalStateException(stylesResponse.toString());
		}
		model.addAttribute("styles", stylesResponse.getValue());
		model.addAttribute("styles_json", toJson(stylesResponse.getValue()));

		GetMagnitudeLimitsResponseDto magnitudeLimitsResponse = eventController
				.getMagnitudeLimits(new GetMagnitudeLimitsRequestDto());
		if (magnitudeLimitsResponse.isException()) {
			throw new IllegalStateException(magnitudeLimitsResponse.toString());
		}
		model.addAttribute("magnitudeLimits",
				magnitudeLimitsResponse.getValue());
		model.addAttribute("magnitudeLimits_json",
				toJson(magnitudeLimitsResponse.getValue()));

		GetDataBoundsResponseDto dataBoundsResponse = eventController
				.getDataBounds(new GetDataBoundsRequestDto());
		if (dataBoundsResponse.isException()) {
			throw new IllegalStateException(dataBoundsResponse.toString());
		}
		model.addAttribute("dataBounds", dataBoundsResponse.getValue());
		model.addAttribute("dataBounds_json",
				toJson(dataBoundsResponse.getValue()));

		return "map";
	}

	/*
	 * This circumvents the same origin restriction, in a production environment
	 * this should be done by the http server itself (possibly Apache)
	 */
	@RequestMapping("layerServer/*")
	public void layerServer(HttpServletRequest request,
			HttpServletResponse response) throws URISyntaxException,
			IOException {
		GetApplicationSettingsResponseDto applicationSettingsResponse = applicationController
				.getSettings(new GetApplicationSettingsRequestDto());
		if (applicationSettingsResponse.isException()) {
			throw new IllegalStateException(
					applicationSettingsResponse.toString());
		}
		String hostAndPort = applicationSettingsResponse.getValue()
				.getLayerServerUri();
		String path = request.getRequestURI();
		path = path.substring(path.indexOf("layerServer/")
				+ "layerServer/".length());
		String query = request.getQueryString();
		String uri = hostAndPort + '/' + path;
		if (query != null) {
			uri += '?' + query;
		}
		URI location = new URI(uri);

		HttpMethod targetRequestMethod = HttpMethod
				.valueOf(request.getMethod());
		ClientHttpRequest targetRequest = httpRequestFactory.createRequest(
				location, targetRequestMethod);
		HttpHeaders targetRequestHeaders = targetRequest.getHeaders();
		targetRequestHeaders.clear();
		for (Enumeration<?> headerNames = request.getHeaderNames(); headerNames
				.hasMoreElements();) {
			String headerName = (String) headerNames.nextElement();
			for (Enumeration<?> headerValues = request.getHeaders(headerName); headerValues
					.hasMoreElements();) {
				String headerValue = (String) headerValues.nextElement();
				targetRequestHeaders.add(headerName, headerValue);
			}
		}
		ClientHttpResponse targetResponse = targetRequest.execute();

		response.setStatus(targetResponse.getStatusCode().value());
		HttpHeaders targetResponseHeaders = targetResponse.getHeaders();
		for (Entry<String, List<String>> headers : targetResponseHeaders
				.entrySet()) {
			String headerName = headers.getKey();
			for (String headerValue : headers.getValue()) {
				response.addHeader(headerName, headerValue);
			}
		}
		InputStream targetResponseBody = targetResponse.getBody();
		OutputStream responseBody = response.getOutputStream();
		IOUtils.copy(targetResponseBody, responseBody);
	}

	@RequestMapping("legend/{sld}")
	@ResponseBody
	public ResponseEntity<Resource> legend(@PathVariable("sld") String sld) {
		return mapController.getLegend(new GetLegendRequestDto(sld));
	}
}

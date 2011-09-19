package com.seismap.service.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.seismap.model.entity.Style;
import com.seismap.model.repository.ApplicationRepository;
import com.seismap.model.repository.StyleRepository;
import com.seismap.service.common.ActorCredentialsDto;
import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ExceptionCause.ExceptionParameter;
import com.seismap.service.style.CreateStyleRequestDto;
import com.seismap.service.style.CreateStyleResponseDto;
import com.seismap.service.style.ListStylesRequestDto;
import com.seismap.service.style.ListStylesResponseDto;
import com.seismap.service.style.StyleDto;
import com.seismap.service.style.StyleService;

public class StyleServiceImpl implements StyleService {

	private ApplicationRepository applicationRepository;

	private StyleRepository styleRepository;

	protected StyleServiceImpl() {
	}

	public StyleServiceImpl(ApplicationRepository applicationRepository,
			StyleRepository styleRepository) {
		this.applicationRepository = applicationRepository;
		this.styleRepository = styleRepository;
	}

	@Transactional
	public ListStylesResponseDto list(ActorCredentialsDto actorCredentials,
			ListStylesRequestDto request) {
		return new ListStylesResponseDto(
				DtoMarshaler.unmarshallStyles(applicationRepository.fetch()
						.getStyles()));
	}

	@Transactional
	public CreateStyleResponseDto create(ActorCredentialsDto actorCredentials,
			CreateStyleRequestDto request) {
		String name = request.getStyleName();
		Style existingStyle = styleRepository.getByName(name);
		if (existingStyle != null) {
			CreateStyleResponseDto exceptionResponse = new CreateStyleResponseDto(
					ExceptionCause.DUPLICATE_STYLE_NAME, "El estilo '" + name
							+ "' ya existe.");
			exceptionResponse.addExceptionParameter(
					ExceptionParameter.STYLE_NAME, name);
			return exceptionResponse;
		}
		String sld = request.getStyleSld();
		Map<String, String> variables = request.getStyleVariables();
		Style style = new Style(sld, name, variables);
		applicationRepository.fetch().getStyles().add(style);
		styleRepository.put(style);
		StyleDto styleDto = DtoMarshaler.unmarshallStyle(style);
		CreateStyleResponseDto response = new CreateStyleResponseDto(styleDto);
		return response;
	}
}

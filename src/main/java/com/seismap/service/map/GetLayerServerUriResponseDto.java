package com.seismap.service.map;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetLayerServerUriResponseDto extends ResponseDto<String> {

	private static final long serialVersionUID = 1L;

	protected GetLayerServerUriResponseDto() {
		super();
	}

	public GetLayerServerUriResponseDto(String uri) {
		super(uri);
	}

	public GetLayerServerUriResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

package com.seismap.service.map;

import java.io.Serializable;
import java.util.List;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class ListUserMapsResponseDto extends ResponseDto<List<MapDto>>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	protected ListUserMapsResponseDto() {

	}

	public ListUserMapsResponseDto(List<MapDto> maps) {
		super(maps);
	}
	
	public ListUserMapsResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

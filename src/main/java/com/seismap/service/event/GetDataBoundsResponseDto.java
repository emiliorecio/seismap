package com.seismap.service.event;

import com.seismap.service.common.ExceptionCause;
import com.seismap.service.common.ResponseDto;

public class GetDataBoundsResponseDto extends ResponseDto<DataBoundsDto> {

	private static final long serialVersionUID = 1L;

	protected GetDataBoundsResponseDto() {
		super();
	}

	public GetDataBoundsResponseDto(DataBoundsDto dataBounds) {
		super(dataBounds);
	}

	public GetDataBoundsResponseDto(ExceptionCause cause, String message) {
		super(cause, message);
	}

}

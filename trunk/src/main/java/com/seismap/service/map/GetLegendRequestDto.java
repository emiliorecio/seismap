package com.seismap.service.map;

import com.seismap.service.common.RequestDto;

public class GetLegendRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	private String sld;

	protected GetLegendRequestDto() {
	}

	public GetLegendRequestDto(String sld) {
		this.sld = sld;
	}

	public String getSld() {
		return sld;
	}

}

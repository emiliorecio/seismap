package com.seismap.service.map;

import com.seismap.service.common.RequestDto;

public class ListUserMapsRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	private Long userId;

	protected ListUserMapsRequestDto() {
	}

	public ListUserMapsRequestDto(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

}

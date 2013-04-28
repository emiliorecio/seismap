package com.seismap.service.user;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class ValidateUserRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String userEmail;

	@JsonProperty
	private String userPassword;

	protected ValidateUserRequestDto() {
	}

	public ValidateUserRequestDto(String userEmail,
			String userPassword) {
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

}

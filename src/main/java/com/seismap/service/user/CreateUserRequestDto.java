package com.seismap.service.user;

import org.codehaus.jackson.annotate.JsonProperty;

import com.seismap.service.common.RequestDto;

public class CreateUserRequestDto extends RequestDto {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String userName;

	@JsonProperty
	private String userEmail;

	@JsonProperty
	private String userPassword;

	protected CreateUserRequestDto() {
	}

	public CreateUserRequestDto(String userName, String userEmail,
			String userPassword) {
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

}

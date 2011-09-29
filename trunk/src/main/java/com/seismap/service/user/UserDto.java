package com.seismap.service.user;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private Long id;

	@JsonProperty
	private String name;

	@JsonProperty
	private String email;

	@JsonProperty
	private Boolean administrator;

	protected UserDto() {
	}

	public UserDto(Long id, String name, String email, Boolean administrator) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.administrator = administrator;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getAdministrator() {
		return administrator;
	}
}

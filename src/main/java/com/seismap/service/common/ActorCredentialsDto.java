package com.seismap.service.common;

import java.io.Serializable;

public class ActorCredentialsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;

	private String passwordHash;

	protected ActorCredentialsDto() {
	}

	public ActorCredentialsDto(Long userId, String passwordHash) {
		this.userId = userId;
		this.passwordHash = passwordHash;
	}

	public Long getUserId() {
		return userId;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

}

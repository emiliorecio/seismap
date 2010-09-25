package com.seismap.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity()
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Agency {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String code;

	public Agency(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

}

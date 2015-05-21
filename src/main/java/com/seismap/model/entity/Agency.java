package com.seismap.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Agency", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Agency implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String code;

	protected Agency() {
	}

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

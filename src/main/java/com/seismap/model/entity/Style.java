package com.seismap.model.entity;

import java.util.Collections;
import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionOfElements;

@Entity
public class Style {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String sld;

	@Column(nullable = false)
	private String name;

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "StyleVariable", joinColumns = @JoinColumn(name = "style_id"))
	@org.hibernate.annotations.MapKey(columns = @Column(name = "name"))
	@Column(name = "value", nullable = false)
	private java.util.Map<String, String> variables = new LinkedHashMap<String, String>();

	protected Style() {
	}

	public Style(String sld, String name,
			java.util.Map<String, String> variables) {
		this.sld = sld;
		this.name = name;
		this.variables = new LinkedHashMap<String, String>(variables);
	}

	public Long getId() {
		return id;
	}

	public String getSld() {
		return sld;
	}

	public String getName() {
		return name;
	}

	public java.util.Map<String, String> getVariables() {
		return Collections.unmodifiableMap(variables);
	}

}

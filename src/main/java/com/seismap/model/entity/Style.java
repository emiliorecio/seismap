package com.seismap.model.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashMap;

@Entity
@Table(name = "style")
public class Style {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String sld;

	@Column(nullable = false)
	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "stylevariable", joinColumns = @JoinColumn(name = "style_id"))
	@MapKeyColumn(name = "name")
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

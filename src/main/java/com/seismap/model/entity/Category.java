package com.seismap.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

@Entity
public class Category implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@IndexColumn(name = "inCategoryIndex")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id", nullable = true)
	private List<Map> maps = new ArrayList<Map>();

	@Transient
	private ListManager<Map> mapsManager = null;

	@Column(nullable = false, insertable = false, updatable = false)
	private Integer inApplicationIndex = null;

	protected Category() {
	}

	public Category(String name) {
		this.name = name;
		this.maps = new ArrayList<Map>();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ListManager<Map> getMapsManager() {
		if (mapsManager == null) {
			mapsManager = new ListManager<Map>(maps);
		}
		return mapsManager;
	}

	public List<Map> getMaps() {
		return getMapsManager().getList();
	}
}

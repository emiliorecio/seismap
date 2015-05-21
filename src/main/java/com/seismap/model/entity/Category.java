package com.seismap.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OrderColumn(name = "inCategoryIndex")
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

package com.seismap.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

@Entity()
public class Category implements Identifiable<Long> {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@IndexColumn(name = "inCategoryIndex")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id", nullable = true)
	private List<Map> maps = new ArrayList<Map>();

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

	public Integer getInApplicationIndex() {
		return inApplicationIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map> getMaps() {
		return Collections.unmodifiableList(maps);
	}

	public boolean moveTo(Map map, int index) {
		int currentIndex = maps.indexOf(map);
		if (currentIndex == -1) {
			return false;
		} else if (currentIndex == index) {
			// nothing to do.
		} else {
			maps.remove(currentIndex);
			maps.add(index, map);
		}
		return true;
	}

	public boolean add(Map map) {
		return add(map, maps.size());
	}

	public boolean add(Map map, int index) {
		int currentIndex = maps.indexOf(map);
		if (currentIndex != -1) {
			return false;
		}
		maps.add(index, map);
		return true;
	}

	public boolean remove(Map map) {
		return maps.remove(map);
	}

	void setInApplicationIndex(Integer inApplicationIndex) {
		this.inApplicationIndex = inApplicationIndex;
	}
}

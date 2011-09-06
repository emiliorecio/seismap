package com.seismap.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;

@Entity
public class Application implements Identifiable<Long> {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@IndexColumn(name = "inApplicationIndex")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "application_id", nullable = false)
	private List<Category> categories = new ArrayList<Category>();

	public Application() {
	}

	public Long getId() {
		return id;
	}

	public List<Category> getCategories() {
		return Collections.unmodifiableList(categories);
	}

	public boolean moveTo(Category category, int index) {
		int currentIndex = categories.indexOf(category);
		if (currentIndex == -1) {
			return false;
		} else if (currentIndex == index) {
			// nothing to do.
		} else {
			categories.remove(currentIndex);
			categories.add(index, category);
		}
		return true;
	}

	public boolean add(Category category) {
		return add(category, categories.size());
	}

	public boolean add(Category category, int index) {
		int currentIndex = categories.indexOf(category);
		if (currentIndex != -1) {
			return false;
		}
		categories.add(index, category);
		return true;
	}

	public boolean remove(Category category) {
		return categories.remove(category);
	}

}

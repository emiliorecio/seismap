package com.seismap.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application")
public class Application implements Identifiable<Long> {

	@Id
	@Column(nullable = false)
	private Long id;

	@OrderColumn(name = "inApplicationIndex")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "application_id", nullable = false)
	private List<Category> categories = new ArrayList<Category>();

	@Transient
	private ListManager<Category> categoriesManager = null;

	@OrderColumn(name = "inApplicationIndex")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "application_id", nullable = false)
	private List<Style> styles = new ArrayList<Style>();

	@Transient
	private ListManager<Style> stylesManager = null;

	@Embedded
	private ApplicationSettings applicationSettings;

	protected Application() {
	}

	protected Application(List<Category> categories,
			List<Style> styles, ApplicationSettings applicationSettings) {
		this.categories = categories;
		this.styles = styles;
		this.applicationSettings = applicationSettings;
	}

	public Long getId() {
		return id;
	}

	public ListManager<Category> getCategoriesManager() {
		if (categoriesManager == null) {
			categoriesManager = new ListManager<Category>(categories);
		}
		return categoriesManager;
	}

	public List<Category> getCategories() {
		return getCategoriesManager().getList();
	}

	public ListManager<Style> getStylesManager() {
		if (stylesManager == null) {
			stylesManager = new ListManager<Style>(styles);
		}
		return stylesManager;
	}

	public List<Style> getStyles() {
		return getStylesManager().getList();
	}

	public ApplicationSettings getApplicationSettings() {
		return applicationSettings;
	}

}

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
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

@Entity()
@Table(name = "SeismapUser")
public class User implements Identifiable<Long> {

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String passwordHash;

	@IndexColumn(name = "inUserIndex")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = true)
	private List<Map> maps = new ArrayList<Map>();

	protected User() {
	}

	public User(String name, String email, String passwordHash) {
		super();
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Long getId() {
		return id;
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

}

package com.seismap.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

@Entity
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

	@Column(nullable = false)
	private boolean administrator;

	@IndexColumn(name = "inUserIndex")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = true)
	private List<Map> maps = new ArrayList<Map>();

	@Transient
	private ListManager<Map> mapsManager = null;

	protected User() {
	}

	public User(String name, String email, String passwordHash,
			boolean administrator) {
		super();
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.administrator = administrator;
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

	public ListManager<Map> getMapsManager() {
		if (mapsManager == null) {
			mapsManager = new ListManager<Map>(maps);
		}
		return mapsManager;
	}

	public List<Map> getMaps() {
		return getMapsManager().getList();
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

}

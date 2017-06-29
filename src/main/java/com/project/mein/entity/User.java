package com.project.mein.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "user", catalog = "srd")
public class User implements java.io.Serializable {
	private Integer userId;
	private String username;
	private String name;
	private String location;
	private String company;
	private String email;
	private String bio;
	private Set<Repository> repositories = new HashSet<Repository>(0);

	public User() {

	}

	public User(Integer userId, String username, String name, String location,
			String company, String email, String bio) {
		super();
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.location = location;
		this.company = company;
		this.email = email;
		this.bio = bio;
	}

	public User(Integer userId, String username, String name, String location,
			String company, String email, String bio,
			Set<Repository> repositories) {
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.location = location;
		this.company = company;
		this.email = email;
		this.bio = bio;
		this.repositories = repositories;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "username", nullable = false, length = 45)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "name", nullable = true, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "location", nullable = true, length = 45)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "company", nullable = true, length = 45)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "email", nullable = true, length = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "bio", nullable = true, length = 250)
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Repository> getRepositories() {
		return repositories;
	}

	@JsonIgnore
	public void setRepositories(Set<Repository> repositories) {
		this.repositories = repositories;
	}

}

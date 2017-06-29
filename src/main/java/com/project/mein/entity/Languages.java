package com.project.mein.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "languages", catalog = "srd")
public class Languages implements java.io.Serializable {
	private Integer languagesId;
	private String name;
	private Double number;
	private Repository repository;

	public Languages() {
	}

	public Languages(Integer languagesId, String name, Double number) {
		super();
		this.languagesId = languagesId;
		this.name = name;
		this.number = number;
	}

	public Languages(Integer languageId, String name, Double number,
			Repository repository) {
		this.languagesId = languageId;
		this.name = name;
		this.number = number;
		this.repository = repository;
	}

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "languages_id", unique = true, nullable = false)
	public Integer getLanguagesId() {
		return languagesId;
	}

	public void setLanguagesId(Integer languagesId) {
		this.languagesId = languagesId;
	}

	@Column(name = "name", nullable = true, length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "number", nullable = true)
	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id", nullable = false)
	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

}

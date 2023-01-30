package com.cathayunited.interview.model;

import javax.persistence.*;

@Entity
@Table(name = "CURRENCY")
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "code")
	private String code;
	@Column(name = "description")
	private String description;
	
	public Currency() {
		
	}
public Currency(String code,String description) {
	this.code = code;
	this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", code=" + code + ", description=" + description + "]";
	}
}
package org.rajnegi.spring.rest.webservices.springrestservice.bean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {

	@Id @GeneratedValue
	private Integer id;
	
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value=@JoinColumn(name = "userid"))
	@JsonIgnore
	private User user;
	
	public Post(Integer id, String description, User user) {
		super();
		this.id = id;
		this.description = description;
		this.user = user;
	}

	public Post() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

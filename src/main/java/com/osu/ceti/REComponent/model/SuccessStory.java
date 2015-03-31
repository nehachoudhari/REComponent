package com.osu.ceti.REComponent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SuccessStory {
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private int id;
	
	@Column
	private String username;
	
	@Column
	private String age;
	
	@Column
	private String weightbefore;
	
	@Column
	private String weightafter;
	
	@Column
	private String work;
	
	@Column
	private String tags;

	public SuccessStory() {
		
	}
	
	public SuccessStory(int id, String username, String age,
			String weightbefore, String weightafter, String work, String tags) {
		super();
		this.id = id;
		this.username = username;
		this.age = age;
		this.weightbefore = weightbefore;
		this.weightafter = weightafter;
		this.work = work;
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWeightbefore() {
		return weightbefore;
	}

	public void setWeightbefore(String weightbefore) {
		this.weightbefore = weightbefore;
	}

	public String getWeightafter() {
		return weightafter;
	}

	public void setWeightafter(String weightafter) {
		this.weightafter = weightafter;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	

}

package com.example.transactionalexamples.model;

import java.util.Arrays;
import java.util.List;

public class Person {
	public Person(Long personId, String name, Skill... skills) {
		this.personId = personId;
		this.name = name;
		this.skills = Arrays.asList(skills);
	}

	private Long personId;

	private String name;

	private List<Skill> skills;

	private boolean isBooked;

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean booked) {
		isBooked = booked;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
}

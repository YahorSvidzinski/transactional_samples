package com.example.transactionalexamples.model;

public class Skill {
	private String name;

	private long knownPercentage;

	public Skill(String name, long knownPercentage) {
		this.name = name;
		this.knownPercentage = knownPercentage;
	}

	public long getKnownPercentage() {
		return knownPercentage;
	}

	public void setKnownPercentage(long knownPercentage) {
		this.knownPercentage = knownPercentage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package com.example.transactionalexamples.repository;

public interface DoctorRepository {

	public Integer countFreeDoctors();

	public void bookDoctorByName(String name);
}

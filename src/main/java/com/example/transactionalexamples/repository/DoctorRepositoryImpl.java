package com.example.transactionalexamples.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DoctorRepositoryImpl implements DoctorRepository{

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Integer countFreeDoctors() {
		//String lockQuery = "SELECT * FROM doctors WHERE on_call = true FOR UPDATE";
		String query = "SELECT COUNT(*) FROM doctors where on_call = true";
		//jdbcTemplate.queryForList(lockQuery);
		return jdbcTemplate.queryForObject(query, Integer.class);
	}

	@Override
	public void bookDoctorByName(String name) {
		String query = "UPDATE doctors SET on_call = false WHERE name = ?";
		jdbcTemplate.update(query, name);
	}
}

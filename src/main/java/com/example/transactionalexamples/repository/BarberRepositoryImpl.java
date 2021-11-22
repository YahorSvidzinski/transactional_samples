package com.example.transactionalexamples.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BarberRepositoryImpl implements BarberRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public boolean isBarberAvailableForTime(Long barberId, String time) {
		String query = "SELECT COUNT(*) FROM barber_book WHERE barber_id = ? AND time = ?";
		Integer count = jdbcTemplate.queryForObject(query, Integer.class, barberId, time);
		return count == 0;
	}

	@Override
	public void bookSlot(String clientName, Long barberId, String time) {
		String query = "INSERT INTO barber_book(client_name, barber_id, time) VALUES (?, ?, ?)";
		jdbcTemplate.update(query, clientName, barberId, time);
	}
}

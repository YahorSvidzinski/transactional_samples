package com.example.transactionalexamples.repository;

public interface BarberRepository {

	public boolean isBarberAvailableForTime(Long barberId, String time);

	public void bookSlot(String clientName, Long barberId, String time);
}

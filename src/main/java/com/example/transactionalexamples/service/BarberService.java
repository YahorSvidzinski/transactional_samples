package com.example.transactionalexamples.service;

import com.example.transactionalexamples.repository.BarberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@AllArgsConstructor
@Slf4j
public class BarberService {

	private final BarberRepository barberRepository;

	@Transactional(propagation = REQUIRES_NEW, isolation = SERIALIZABLE)
	public void bookFreeBarberSlot(String clientName, Long barberId, String time) {
		boolean barberAvailable = barberRepository.isBarberAvailableForTime(barberId, time);
		if (barberAvailable) {
			barberRepository.bookSlot(clientName, barberId, time);
			log.info("Slot booked for client " + clientName);
		}
	}
}

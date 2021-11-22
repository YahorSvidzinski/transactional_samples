package com.example.transactionalexamples.service;

import com.example.transactionalexamples.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.*;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorService {

	private final DoctorRepository doctorRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = REPEATABLE_READ)
	public void bookFreeDoctor(String name) {
		Integer count = doctorRepository.countFreeDoctors();
		log.info("Doctor count for " + name + " transaction is: " + count);
		if (count >= 2) {
			doctorRepository.bookDoctorByName(name);
			log.info("Doctor "+ name + " is booked");
		}
	}
}

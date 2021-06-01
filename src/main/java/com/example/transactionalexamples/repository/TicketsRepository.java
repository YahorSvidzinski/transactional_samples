package com.example.transactionalexamples.repository;

import com.example.transactionalexamples.model.Tickets;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TicketsRepository extends CrudRepository<Tickets, Long> {

	boolean existsByPlaceAndPrice(String place, Integer price);

	List<Tickets> getTicketsByPlace(String place);

	boolean existsByPrice(Integer price);

	Integer countTicketsByPrice(Integer price);

	List<Tickets> getTicketsByPrice(Integer price);
}

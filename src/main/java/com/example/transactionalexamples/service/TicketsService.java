package com.example.transactionalexamples.service;

import com.example.transactionalexamples.model.Tickets;
import com.example.transactionalexamples.repository.TicketsRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketsService {

	private final TicketsRepository ticketsRepository;

	@PersistenceContext
	private final EntityManager entityManager;

	@SneakyThrows
	@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
	public void buyerBookTicket(String place, Integer price) {
		log.info("Start Buyer Transaction");
		if (ticketsRepository.existsByPlaceAndPrice(place, price)) {
			TimeUnit.SECONDS.sleep(1);
			final Tickets ticket = ticketsRepository.getTicketsByPlace(place).stream().findFirst().orElseThrow(() -> new RuntimeException("Ticket not found"));
			log.info("Ticket found for buyer transcation! Price is: " + ticket.getPrice());
			//buy ticket
			ticketsRepository.delete(ticket);
			log.info("Ticket booked for buyer transaction!");
		} else {
			throw new RuntimeException();
		}
	}

	@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
	public void adminChangeTicketPrice(String place, Integer price) {
		log.info("Start Admin Transaction");
		if (ticketsRepository.existsByPlaceAndPrice(place, price)) {
			final Tickets ticket = ticketsRepository.getTicketsByPlace(place).stream().findFirst().orElseThrow(() -> new RuntimeException("Ticket not found"));
			log.info("Ticket found for admin transaction!");
			ticket.setPrice(10000000);
			ticketsRepository.save(ticket);
			log.info("Ticket price updated in admin transaction!");
		} else {
			throw new RuntimeException();
		}
	}

	@SneakyThrows
	@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
	public void buyerBookTicketsForSpecificPrice(Integer price, Integer count) {
		log.info("Start buyer transaction");
		if (ticketsRepository.countTicketsByPrice(price).equals(count)) {
			TimeUnit.SECONDS.sleep(1);
			final List<Tickets> tickets = ticketsRepository.getTicketsByPrice(price);
			log.info("Tickets number is " + tickets.size() + ". Buyer does not expected to buy 3 tickets");
		}
		log.info("end");
	}

	@SneakyThrows
	@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
	public void adminAddTicket(String place, Integer price) {
		log.info("Start Admin Transaction");
		Tickets tickets = new Tickets(4L, place, price);
		TimeUnit.MILLISECONDS.sleep(200);
		ticketsRepository.save(tickets);
		log.info("Admin added one more ticket for price 100");
	}

	@SneakyThrows
	@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
	public void adminDeleteTicket(String place, Integer price) {
		log.info("Start Admin Transaction");
		TimeUnit.MILLISECONDS.sleep(200);
		ticketsRepository.deleteById(3L);
		log.info("Admin deleted one more ticket for price 100");
	}
}

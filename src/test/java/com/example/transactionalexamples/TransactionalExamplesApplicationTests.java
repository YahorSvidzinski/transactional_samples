package com.example.transactionalexamples;

import com.example.transactionalexamples.service.CommentsService;
import com.example.transactionalexamples.service.PostsService;
import com.example.transactionalexamples.service.TicketsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TransactionalExamplesApplicationTests {

	@Autowired
	PostsService postService;

	@Autowired
	CommentsService commentsService;

	@Autowired
	TicketsService ticketsService;

	@Test
	void nonRepeatableReadPhenomena() {
		Thread buyerThread = new Thread(() -> ticketsService.buyerBookTicket("B23", 100));
		Thread adminThread = new Thread(() -> ticketsService.adminChangeTicketPrice("B23", 100));
		adminThread.start();
		buyerThread.start();

		while (buyerThread.isAlive() && adminThread.isAlive() || buyerThread.getState().equals(Thread.State.TIMED_WAITING)) {
			log.debug("Tasks is not yet finished...");
		}
	}

	@Test
	void phantomReadPhenomena() {
		Thread buyerThread = new Thread(() -> ticketsService.buyerBookTicketsForSpecificPrice(100, 2));
		Thread adminThread = new Thread(() -> ticketsService.adminAddTicket("B43", 100));
		buyerThread.start();
		adminThread.start();

		while (buyerThread.isAlive()
				&& adminThread.isAlive()
				|| buyerThread.getState().equals(Thread.State.TIMED_WAITING)
				|| adminThread.getState().equals(Thread.State.TIMED_WAITING)) {
			log.debug("Tasks is not yet finished...");
		}
	}

//	@Test
//	void phantomReadPhenomena() {
//		commentsService.phantomReadPhenomena();
//	}

	@Test
	void lostUpdatePhenomena() {
		postService.lostUpdatePhenomena(1L);
	}
}

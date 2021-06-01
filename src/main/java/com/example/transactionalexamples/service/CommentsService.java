package com.example.transactionalexamples.service;

import com.example.transactionalexamples.model.Comments;
import com.example.transactionalexamples.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsService {

	@PersistenceContext
	private final EntityManager entityManager;

	private final CommentsRepository commentsRepository;

	@Lazy
	@Autowired
	private CommentsService commentsService;

	@Transactional(isolation = REPEATABLE_READ)
	public void phantomReadPhenomena() {
		//BEGIN BOB TRANSACTION
		final Iterable<Comments> intialComments = commentsRepository.findAll();
		log.info("Initial comments: " + intialComments);

		commentsService.aliceInsertComment();
		log.info("New comment added");

		final Iterable<Comments> updatedComments = commentsRepository.findAll();
		log.info("Updated comments: " + updatedComments);
		//COMMIT BOB TRANSACTION
	}

	@Transactional(propagation = REQUIRES_NEW, isolation = REPEATABLE_READ)
	public void aliceInsertComment() {
		//BEGIN ALICE TRANSACTION
		Comments comment = new Comments(4L, 4L);
		commentsRepository.save(comment);
		//COMMIT ALICE TRANSACTION
	}
}

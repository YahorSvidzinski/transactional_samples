package com.example.transactionalexamples.service;

import com.example.transactionalexamples.model.Posts;
import com.example.transactionalexamples.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

	@PersistenceContext
	private final EntityManager entityManager;

	private final PostsRepository postRepository;

	@Lazy
	@Autowired
	private PostsService postsService;

	@Transactional(isolation = READ_COMMITTED)
	public void nonRepeatableReadPhenomena(Long id) {
		final Posts initialPost = postRepository.findById(id).get(); //BEGIN BOB TRANSACTION
		log.info("Initial post " + initialPost);
		postsService.aliceUpdatePost(id);
		entityManager.detach(initialPost);
		log.info("Post updated");
		final Posts postCheck = postRepository.findById(id).get();
		log.info("Post after update " + postCheck); //COMMIT BOB TRANSACTION
	}

	@Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
	public void aliceUpdatePost(Long id) {
		final Posts post = postRepository.findById(id).get(); //BEGIN ALICE TRANSACTION
		post.setTitle("ACID");
		postRepository.save(post); //COMMIT ALICE TRANSACTION
	}

	public void lostUpdatePhenomena(Long id) {
		final Posts initialPost = postRepository.findById(id).get(); //BEGIN BOB TRANSACTION
		log.info("Initial post " + initialPost);
		postsService.aliceUpdatePostPhantom(id);
		postsService.bobUpdatePostPhantom(id);
		final Posts postCheck = postRepository.findById(id).get();
		log.info("Post Check " + postCheck);
	}

	@Transactional(isolation = READ_COMMITTED, propagation = REQUIRES_NEW)
	public void aliceUpdatePostPhantom(Long id) {
		final Posts post = postRepository.findById(id).get();
		post.setTitle("ACID");
		postsService.bobUpdatePostPhantom(id);
	}

	@Transactional(isolation = READ_COMMITTED, propagation = REQUIRES_NEW)
	public void bobUpdatePostPhantom(Long id) {
		final Posts post = postRepository.findById(id).get();
		post.setTitle("BOBACID");
	}
}
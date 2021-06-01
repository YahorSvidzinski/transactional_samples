package com.example.transactionalexamples.repository;

import com.example.transactionalexamples.model.Posts;
import org.springframework.data.repository.CrudRepository;

public interface PostsRepository extends CrudRepository<Posts, Long> {
}

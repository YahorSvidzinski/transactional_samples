package com.example.transactionalexamples.repository;

import com.example.transactionalexamples.model.Comments;
import org.springframework.data.repository.CrudRepository;

public interface CommentsRepository extends CrudRepository<Comments, Long> {
}

package com.example.transactionalexamples.repository;

import com.example.transactionalexamples.model.Persons;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository {

    List<Persons> findByName(String name);

    List<Persons> findByNameWithLock(String name);

    Optional<Persons> findByIdWithLock(Long id);

    void save(Persons persons);

    Optional<Persons> findById(Long id);
}
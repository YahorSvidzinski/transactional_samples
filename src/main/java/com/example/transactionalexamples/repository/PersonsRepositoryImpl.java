package com.example.transactionalexamples.repository;

import com.example.transactionalexamples.mapper.PersonsRowMapper;
import com.example.transactionalexamples.model.Persons;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PersonsRepositoryImpl implements PersonsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Persons> findByName(String name) {
        String query = "SELECT * FROM persons WHERE name = ?";
        return jdbcTemplate.query(query, new PersonsRowMapper(), name);
    }

    @Override
    public List<Persons> findByNameWithLock(String name) {
        String query = "SELECT * FROM persons WHERE name = ? FOR UPDATE";
        return jdbcTemplate.query(query, new PersonsRowMapper(), name);
    }

    @Override
    public Optional<Persons> findByIdWithLock(Long id) {
        String query = "SELECT * FROM persons WHERE person_id = ? FOR UPDATE";
        return jdbcTemplate.query(query, new PersonsRowMapper(), id).stream().findFirst();
    }

    @Override
    public void save(Persons persons) {
        String query = "INSERT INTO persons(name, email) VALUES(?, ?)";
        jdbcTemplate.update(query, persons.getName(), persons.getEmail());
    }

    @Override
    public Optional<Persons> findById(Long id) {
        String query = "SELECT * FROM persons WHERE person_id = ?";
        return jdbcTemplate.query(query, new PersonsRowMapper(), id).stream().findFirst();
    }
}

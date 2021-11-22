package com.example.transactionalexamples.mapper;

import com.example.transactionalexamples.model.Persons;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonsRowMapper implements RowMapper<Persons> {

    @Override
    public Persons mapRow(ResultSet resultSet, int i) throws SQLException {
        Persons persons = new Persons();
        persons.setId(resultSet.getLong("person_id"));
        persons.setName(resultSet.getString("name"));
        persons.setEmail(resultSet.getString("email"));
        return persons;
    }
}

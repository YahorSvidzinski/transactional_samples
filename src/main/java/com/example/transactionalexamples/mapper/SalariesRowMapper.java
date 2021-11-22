package com.example.transactionalexamples.mapper;

import com.example.transactionalexamples.model.Persons;
import com.example.transactionalexamples.model.Salaries;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalariesRowMapper implements RowMapper<Salaries> {

    @Override
    public Salaries mapRow(ResultSet resultSet, int i) throws SQLException {
        Persons persons = new Persons();
        persons.setId(resultSet.getLong("person_id"));
        persons.setName(resultSet.getString("name"));
        persons.setEmail(resultSet.getString("email"));

        Salaries salaries = new Salaries();
        salaries.setId(resultSet.getLong("salary_id"));
        salaries.setDepartment(resultSet.getString("department"));
        salaries.setRate(resultSet.getDouble("rate"));
        salaries.setPerson(persons);

        return salaries;
    }
}

package com.example.transactionalexamples.repository;

import com.example.transactionalexamples.mapper.SalariesRowMapper;
import com.example.transactionalexamples.model.Persons;
import com.example.transactionalexamples.model.Salaries;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SalariesRepositoryImpl implements SalariesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Salaries> findByDepartment(String department) {
        String query = "SELECT * FROM salaries INNER JOIN persons p on p.person_id = salaries.person_id WHERE department = ?";
        return jdbcTemplate.query(query, new SalariesRowMapper(), department);
    }

    @Override
    public void save(Salaries salary) {
        String query = "INSERT INTO salaries(department, rate, person_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, salary.getDepartment(), salary.getRate(), salary.getPerson().getId());
    }
}

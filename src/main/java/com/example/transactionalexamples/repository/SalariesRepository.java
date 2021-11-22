package com.example.transactionalexamples.repository;

import com.example.transactionalexamples.model.Salaries;

import java.util.List;

public interface SalariesRepository {

    List<Salaries> findByDepartment(String department);

    void save(Salaries salaries);
}

package com.example.transactionalexamples.service;

import com.example.transactionalexamples.model.Persons;
import com.example.transactionalexamples.model.Salaries;
import com.example.transactionalexamples.repository.PersonsRepository;
import com.example.transactionalexamples.repository.SalariesRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalariesService {

    private final SalariesRepository salariesRepository;
    private final PersonsRepository personsRepository;

    @SneakyThrows
    @Transactional(propagation = REQUIRES_NEW, isolation = REPEATABLE_READ)
    public void salaryCalculationService() {
        var frontendDepartment = salariesRepository.findByDepartment("Frontend");
        frontendDepartment.forEach(salary -> {
            calculateSalary(salary.getRate());
            log.info("Salary was sent for Frontend department members :" + salary.getPerson());
        });
        Thread.sleep(1000);
        var backendDepartment = salariesRepository.findByDepartment("Backend");
        backendDepartment.forEach(salary -> {
            calculateSalary(salary.getRate());
            log.info("Salary was sent for Backend department members :" + salary.getPerson());
        });
    }

    @SneakyThrows
    public void job() {
        System.out.println("job method");
        Thread.sleep(5000);
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void method() {
        Persons persons = new Persons();
        persons.setId(2L);
        persons.setName("Egor");
        personsRepository.save(persons);
    }

    @SneakyThrows
    @Transactional
    public void find() {
        Thread.sleep(1000);
        final Optional<Persons> byId = personsRepository.findById(2L);
        System.out.println(byId.get().getName());
    }

    @SneakyThrows
    @Transactional(propagation = REQUIRES_NEW, isolation = REPEATABLE_READ)
    public void updateNameForPerson(String oldName, String newName) {
        var person = personsRepository.findByName(oldName)
                .stream().findFirst().orElseThrow();
        person.setName(newName);
        personsRepository.save(person);
        log.info("Administrator updated person name");
        log.info("Updated version: " + personsRepository.findById(1L));
    }

    @SneakyThrows
    @Transactional(propagation = REQUIRES_NEW, isolation = REPEATABLE_READ)
    public void newSalaryCalculationService() {
        var frontendDepartment = salariesRepository.findByDepartment("Frontend");
        frontendDepartment.forEach(salary -> {
            calculateSalary(salary.getRate());
            log.info("Salary was sent for Frontend department member :" + salary.getPerson());
        });
        Thread.sleep(1000);
        var backendDepartment = salariesRepository.findByDepartment("Backend");
        backendDepartment.forEach(salary -> {
            calculateSalary(salary.getRate());
            log.info("Salary was sent for Backend department member :" + salary.getPerson());
        });
    }

    @SneakyThrows
    @Transactional(propagation = REQUIRES_NEW, isolation = REPEATABLE_READ)
    public void addNewPerson(Persons person, Salaries salary) {
        Thread.sleep(100);
        personsRepository.save(person);
        salariesRepository.save(salary);
        log.info("New person added");
    }

    @SneakyThrows
    @Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
    public void lostUpdateNameForPerson(String oldName, String newName) {
        Thread.sleep(100);
        var person = personsRepository.findByName(oldName)
                .stream().findFirst().orElseThrow();
        person.setName(newName);
        personsRepository.save(person);
        log.info("Updated person: " + personsRepository.findById(1L));
    }

    @SneakyThrows
    @Transactional(propagation = REQUIRES_NEW, isolation = READ_COMMITTED)
    public void updatePersonEmail(Long personId, String email) {
        var person = personsRepository.findById(personId).orElseThrow();
        Thread.sleep(1000);
        person.setEmail(email);
        personsRepository.save(person);
        log.info("Updated person: " + personsRepository.findById(1L));
    }

    public double calculateSalary(Double rate) {
        return rate * 100;
    }
}
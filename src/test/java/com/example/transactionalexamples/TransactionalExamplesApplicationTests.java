package com.example.transactionalexamples;

import com.example.transactionalexamples.model.Persons;
import com.example.transactionalexamples.model.Salaries;
import com.example.transactionalexamples.repository.SalariesRepository;
import com.example.transactionalexamples.service.BarberService;
import com.example.transactionalexamples.service.DoctorService;
import com.example.transactionalexamples.service.SalariesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TransactionalExamplesApplicationTests {

    @Autowired
    SalariesService salariesService;

    @Autowired
    SalariesRepository salariesRepository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    BarberService barberService;

    @Test
    void nonRepeatableReadPhenomena() {
        Thread buyerThread = new Thread(() -> salariesService.salaryCalculationService());
        Thread adminThread = new Thread(() -> salariesService.updateNameForPerson("Vasya Pupkin", "Vasya Lupkin"));
        buyerThread.start();
        adminThread.start();

        while (buyerThread.isAlive() || adminThread.isAlive() || buyerThread.getState().equals(Thread.State.TIMED_WAITING)) {
            log.debug("Tasks is not yet finished...");
        }
    }

    @Test
    void phantomReadPhenomena() {
        Persons persons = new Persons(2L, "Ivan Novichok");
        Salaries salaries = new Salaries(3L, "Backend", 1.5, persons);
        Thread buyerThread = new Thread(() -> salariesService.newSalaryCalculationService());
        Thread adminThread = new Thread(() -> salariesService.addNewPerson(persons, salaries));
        buyerThread.start();
        adminThread.start();

        while (buyerThread.isAlive()
                || adminThread.isAlive()
                || buyerThread.getState().equals(Thread.State.TIMED_WAITING)
                || adminThread.getState().equals(Thread.State.TIMED_WAITING)) {
            log.debug("Tasks is not yet finished...");
        }
    }

    @Test
    void lostUpdatePhenomena() {

        Thread firstThread = new Thread(() -> salariesService.lostUpdateNameForPerson("Vasya Pupkin", "Vasya Lupkin"));
        Thread secondThread = new Thread(() -> salariesService.updatePersonEmail(1L, "vasya.lupkin@gmail.com"));


        firstThread.start();
        secondThread.start();

        while (firstThread.isAlive()
                || secondThread.isAlive()
                || firstThread.getState().equals(Thread.State.TIMED_WAITING)
                || secondThread.getState().equals(Thread.State.TIMED_WAITING)) {
            log.debug("Tasks is not yet finished...");
        }
    }

    @Test
    void doctorSimulation() {

        Thread firstThread = new Thread(() -> doctorService.bookFreeDoctor("Alice"));
        Thread secondThread = new Thread(() -> doctorService.bookFreeDoctor("Bob"));


        firstThread.start();
        secondThread.start();

        while (firstThread.isAlive()
                || secondThread.isAlive()
                || firstThread.getState().equals(Thread.State.TIMED_WAITING)
                || secondThread.getState().equals(Thread.State.TIMED_WAITING)) {
            log.debug("Tasks is not yet finished...");
        }
    }

    @Test
    void barberSimulation() {

        Thread firstThread = new Thread(() -> barberService.bookFreeBarberSlot("Egor", 1L, "16:00"));
        Thread secondThread = new Thread(() -> barberService.bookFreeBarberSlot("Bob", 1L, "16:00"));


        firstThread.start();
        secondThread.start();

        while (firstThread.isAlive()
                || secondThread.isAlive()
                || firstThread.getState().equals(Thread.State.TIMED_WAITING)
                || secondThread.getState().equals(Thread.State.TIMED_WAITING)) {
            log.debug("Tasks is not yet finished...");
        }
    }
}

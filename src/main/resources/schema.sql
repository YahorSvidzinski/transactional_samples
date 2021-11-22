DROP TABLE IF EXISTS salaries;
DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS barber_book;
DROP TABLE IF EXISTS barbers;
CREATE TABLE persons(
    person_id INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255),
    email VARCHAR(255),
    PRIMARY KEY(person_id)
);

CREATE TABLE salaries(
    salary_id INT GENERATED ALWAYS AS IDENTITY,
    department VARCHAR(255) NOT NULL,
    rate real,
    person_id INT,
    CONSTRAINT fk_persons
    FOREIGN KEY (person_id)
    REFERENCES persons(person_id),
    PRIMARY KEY(salary_id)
);
CREATE TABLE doctors(
    doctor_id INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255),
    on_call BOOLEAN NOT NULL,
    PRIMARY KEY(doctor_id)
);
CREATE TABLE barbers(
    barber_id INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255),
    PRIMARY KEY(barber_id)
);
CREATE TABLE barber_book(
    book_id INT GENERATED ALWAYS AS IDENTITY,
    client_name VARCHAR(255),
    barber_id INT,
    time VARCHAR(255),
    CONSTRAINT fk_barbers
    FOREIGN KEY (barber_id)
    REFERENCES barbers(barber_id),
    PRIMARY KEY(book_id)
);
INSERT INTO barbers(name) VALUES ('Anton');
INSERT INTO persons(name) VALUES ('Vasya Pupkin');
INSERT INTO barber_book(client_name, barber_id, time) VALUES ('Egor', '1', '14:30');
INSERT INTO salaries(department, rate, person_id) VALUES ('Frontend', '1.4', '1');
INSERT INTO salaries(department, rate, person_id) VALUES ('Backend', '1.5', '1');
INSERT INTO doctors(name, on_call) VALUES ('Alice', true);
INSERT INTO doctors(name, on_call) VALUES ('Bob', true);
INSERT INTO doctors(name, on_call) VALUES ('Carol', false);
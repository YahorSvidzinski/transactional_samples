package com.example.transactionalexamples.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salaries implements Serializable {

    private Long id;

    private String department;

    private Double rate;

    private Persons person;
}

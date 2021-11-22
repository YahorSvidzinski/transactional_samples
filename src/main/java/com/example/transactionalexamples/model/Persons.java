package com.example.transactionalexamples.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Persons implements Serializable {


    private Long id;

    private String name;

    private String email;

    private Long version;

    public Persons(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

package com.example.demo.errors;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Integer id) {
        super("Person with id " + id + " not found");
    }
}

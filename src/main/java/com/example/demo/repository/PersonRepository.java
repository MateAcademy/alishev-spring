package com.example.demo.repository;

import com.example.demo.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> findAll();
    Optional<Person> findById(long id);
    Optional<Person> findByEmail(String email);
    void save(Person person);
    void update(Person person);
    void delete(long id);
    void butchSaveAll(List<Person> people);
}

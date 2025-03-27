package com.example.demo.utils;

import com.example.demo.dto.PersonResponse;
import com.example.demo.models.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PeopleMapper {

    public List<PersonResponse> mapToPersonResponseList(List<Person> people) {
        return people.stream()
            .map(person -> new PersonResponse(person.getId(), person.getName()))
            .collect(Collectors.toList());
    }

    public PersonResponse mapToPersonResponse(Person person) {
        return new PersonResponse(person.getId(), person.getName(), person.getAge(), person.getEmail(), person.getCity());
    }
}
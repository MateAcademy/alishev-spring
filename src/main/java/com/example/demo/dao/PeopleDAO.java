package com.example.demo.dao;

import com.example.demo.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PeopleDAO {

    private static int PEOPLE_COUNT;

    private List<Person> people = new ArrayList<>() {{
        add(new Person(++PEOPLE_COUNT, "Alla", 10, "alla@gmail.com", "Kiev"));
        add(new Person(++PEOPLE_COUNT, "Tom", 20, "tom@gmail.com", "Kiev"));
        add(new Person(++PEOPLE_COUNT, "Bob", 30, "bob@gmail.com", "Lviv"));
    }};

    public List<Person> getAllPeople() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
    }

    public void save(Person person) {
        if (person.getId() == null) {
            person.setId(++PEOPLE_COUNT);
            people.add(person);
        }
    }

    public void update(Integer id, Person updatedPerson) {
        if (updatedPerson.getId() == null) {
            throw new RuntimeException("id is null");
        } else if (Objects.equals(id, updatedPerson.getId())) {
            Person personFromDB = this.show(updatedPerson.getId());
            personFromDB.setName(updatedPerson.getName());
            personFromDB.setAge(updatedPerson.getAge());
            personFromDB.setEmail(updatedPerson.getEmail());
            personFromDB.setCity(updatedPerson.getCity());
        }
    }

    public void delete(Integer id) {
        people.removeIf(person -> person.getId() == id);
    }
}

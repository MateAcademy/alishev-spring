package com.example.demo.repository;

import com.example.demo.models.Person;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Profile("arrayList")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonArrayListRepository implements PersonRepository {

    static Long PEOPLE_COUNT;

    private List<Person> people = new ArrayList<>() {{
        add(new Person(++PEOPLE_COUNT, "Alla", 10, "alla@gmail.com", "Kiev"));
        add(new Person(++PEOPLE_COUNT, "Tom", 20, "tom@gmail.com", "Kiev"));
        add(new Person(++PEOPLE_COUNT, "Bob", 30, "bob@gmail.com", "Lviv"));
    }};


    public List<Person> findAll() {
        return people;
    }

    public Optional<Person> findById(long id) {
        return people.stream().filter(person -> person.getPerson_id() == id).findFirst();
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return Optional.empty();
    }

    public void save(Person person) {
        if (person.getPerson_id() == null) {
            person.setPerson_id(++PEOPLE_COUNT);
            people.add(person);
        }
    }

    public void update(Person updatedPerson) {
        if (updatedPerson.getPerson_id() == null) {
            throw new RuntimeException("id is null");
        } else  {
            Person personFromDB = this.findById(updatedPerson.getPerson_id()).get();
            personFromDB.setName(updatedPerson.getName());
            personFromDB.setAge(updatedPerson.getAge());
            personFromDB.setEmail(updatedPerson.getEmail());
            personFromDB.setAddress(updatedPerson.getAddress());
        }
    }

    public void delete(long id) {
        people.removeIf(person -> person.getPerson_id() == id);
    }

    @Override
    public void butchSaveAll(List<Person> people) {
        //todo: метод здесь не работает
    }
}

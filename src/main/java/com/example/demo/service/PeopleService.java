package com.example.demo.service;

import com.example.demo.dto.PersonResponse;
import com.example.demo.errors.PersonNotFoundException;
import com.example.demo.models.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.utils.PeopleMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleService {

    final PersonRepository personRepository;
    final PeopleMapper peopleMapper;

    public List<PersonResponse> getAllPeople() {
        List<Person> allPeople = personRepository.findAll();
        return peopleMapper.mapToPersonResponseList(allPeople);
    }

    public PersonResponse getPersonById(@NonNull Integer person_id) {
        Person getPersonById = personRepository.findById(person_id)
            .orElseThrow(() -> new PersonNotFoundException(person_id));

        return peopleMapper.mapToPersonResponse(getPersonById);
    }

    public void save(@NonNull Person person) {
        personRepository.save(person);
        System.out.println("PeopleService.class save person to DB personId = " + person.getPerson_id());
    }

    public void update(@NonNull Integer id, @NonNull Person updatedPerson) {
        personRepository.update(updatedPerson);
    }

    public void delete(@NonNull Integer id) {
        personRepository.delete(id);
    }

    public void insert1000People() {
        List<Person> people = get1000People();

        long start = System.currentTimeMillis();

        for (Person person : people) {
            personRepository.save(person);
        }

        long end = System.currentTimeMillis();
        System.out.println("⏱ Обычная вставка 1000 записей заняла: " + (end - start) + " мс");

    }

    public void butch_insert1000People() {
        List<Person> people = get1000People();

        long start = System.currentTimeMillis();

        personRepository.butchSaveAll(people); // ❗ убедись, что репозиторий поддерживает saveAll

        long end = System.currentTimeMillis();
        System.out.println("⏱ Batch вставка 1000 записей заняла: " + (end - start) + " мс");
    }

    private List<Person> get1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 1; i < 1001; i++) {
            people.add(new Person("persone_" + i, 10, "persone_" + i + "@gmail.com", "city_" + i));
        }
        return people;
    }
}

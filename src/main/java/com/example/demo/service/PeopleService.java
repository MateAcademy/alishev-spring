package com.example.demo.service;

import com.example.demo.repository.PersonJdbcTemplateRepository;
import com.example.demo.dto.PersonResponse;
import com.example.demo.errors.PersonNotFoundException;
import com.example.demo.models.Person;
import com.example.demo.utils.PeopleMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleService {

    // final PeopleDAO peopleDAO;
    // final PersonRepositoryJdbc personRepositoryJdbc;
    final PersonJdbcTemplateRepository personJdbcTemplateRepository;
    final PeopleMapper peopleMapper;

    public List<PersonResponse> getAllPeople() {
        // List<Person> allPeople = peopleDAO.getAllPeople();
        // List<Person> allPeople = personRepositoryJdbc.findAll();
        List<Person> allPeople = personJdbcTemplateRepository.findAll();
        return peopleMapper.mapToPersonResponseList(allPeople);
    }

    public PersonResponse getPersonById(@NonNull Integer id) {
        // Person getPersonById = peopleDAO.show(id);
        // Person getPersonById = personRepositoryJdbc.show(id);
        Person person = personJdbcTemplateRepository.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(id));

        return peopleMapper.mapToPersonResponse(person);
    }

    public void save(@NonNull Person person) {
        // peopleDAO.save(person);
        //personRepositoryJdbc.save(person);
        personJdbcTemplateRepository.save(person);
        System.out.println("PeopleService.class save person to DB personId = " + person.getId());
    }

    public void update(@NonNull Integer id, @NonNull Person updatedPerson) {
        // peopleDAO.update(id, updatedPerson);
        // personRepositoryJdbc.update(id, updatedPerson);
        personJdbcTemplateRepository.update(updatedPerson);
    }

    public void delete(@NonNull Integer id) {
        // peopleDAO.delete(id);
        // personRepositoryJdbc.delete(id);
        personJdbcTemplateRepository.delete(id);
    }
}

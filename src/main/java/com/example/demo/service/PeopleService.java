package com.example.demo.service;

import com.example.demo.dao.PeopleDAO;
import com.example.demo.dto.PersonResponse;
import com.example.demo.models.Person;
import com.example.demo.utils.PeopleMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleService {

    final PeopleDAO peopleDAO;
    final PeopleMapper peopleMapper;

    public List<PersonResponse> getAllPeople() {
        List<Person> allPeople = peopleDAO.getAllPeople();
        return peopleMapper.mapToPersonResponseList(allPeople);
    }

    public PersonResponse getPersonById(@NonNull Integer id) {
        Person getPersonById = peopleDAO.show(id);
        return peopleMapper.mapToPersonResponse(getPersonById);
    }

    public void save(@NonNull Person person) {
        peopleDAO.save(person);
    }

    public void update(@NonNull Integer id, @NonNull Person updatedPerson) {
        peopleDAO.update(id, updatedPerson);
    }

    public void delete(@NonNull Integer id) {
        peopleDAO.delete(id);
    }
}

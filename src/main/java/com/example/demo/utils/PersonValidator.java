package com.example.demo.utils;

import com.example.demo.models.Person;
import com.example.demo.repository.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    final PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personRepository.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "400 Error", "Person with this email already exist");
        }
    }
}

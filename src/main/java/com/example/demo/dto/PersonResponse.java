package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonResponse {

    Integer id;
    String name;
    Integer age;
    String email;
    String city;

    public PersonResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public PersonResponse(String name, Integer age, String email, String city) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.city = city;
    }
}

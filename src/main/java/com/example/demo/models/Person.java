package com.example.demo.models;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String name;

    @Min(value = 0, message = "Age should be greater then 0")
    @Max(value = 120, message = "Age should be less then 120")
    Integer age;

    @NotEmpty(message = "Email should not be empty")
    @Email
    String email;

    @NotEmpty(message = "City should not be empty")
    @Size(min = 10, max = 50, message = "City should be between 2 and 30 characters")
    @Pattern(
        regexp = "^\\p{Lu}\\p{Ll}+, \\p{Lu}\\p{Ll}+, \\d{6}$",
        message = "Адрес должен быть в формате: 'Страна, Город, 123456'"
    )
    String address;

    public Person(String name, Integer age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

}

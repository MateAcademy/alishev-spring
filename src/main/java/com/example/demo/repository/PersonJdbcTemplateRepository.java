package com.example.demo.repository;

import com.example.demo.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    // Это такой объект который отображает строки из таблицы в нашу сущность Person
    private final RowMapper<Person> personRowMapper = (rs, rowNum) -> new Person(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("email"),
            rs.getString("city")
    );

    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, personRowMapper);
    }

    public Optional<Person> findById(int id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        List<Person> people = jdbcTemplate.query(sql, personRowMapper, id);
        // можно возвращать ошибку что просто человек с таким айди не был найден
        return people.stream().findFirst();
    }

    public void save(Person person) {
        String sql = "INSERT INTO person(name, age, email, city) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getCity());
    }

    public void update(Person person) {
        String sql = "UPDATE person SET name = ?, age = ?, email = ?, city = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getCity(),
                person.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

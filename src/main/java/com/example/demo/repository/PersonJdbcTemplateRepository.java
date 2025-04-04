package com.example.demo.repository;

import com.example.demo.models.Person;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
@Profile("jdbc-template")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonJdbcTemplateRepository implements PersonRepository {

    final JdbcTemplate jdbcTemplate;

    // This is an object that maps rows from the table to our Person entity.
    private final RowMapper<Person> personRowMapper = (rs, rowNum) -> new Person(
            rs.getLong("person_id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("email"),
            rs.getString("address")
    );

    public List<Person> findAll() {
        System.out.println("findAll in jdbc-template");
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, personRowMapper);
    }

    public Optional<Person> findById(long person_id) {
        String sql = "SELECT * FROM person WHERE person_id = ?";
        List<Person> people = jdbcTemplate.query(sql, personRowMapper, person_id);
        //todo: you can return an error that a person with such an ID was simply not found
        return people.stream().findFirst();
    }

    public Optional<Person> findByEmail(String email) {
        String sql = "SELECT * FROM person WHERE email = ?";
        List<Person> people = jdbcTemplate.query(sql, personRowMapper, email);
        return people.stream().findFirst();
    }

    public void save(Person person) {
        String sql = "INSERT INTO person(name, age, email, address) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress());
    }

    public void update(Person person) {
        String sql = "UPDATE person SET name = ?, age = ?, email = ?, address = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress(),
                person.getPerson_id());
    }

    public void delete(long id) {
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    @Transactional
    public void butchSaveAll(List<Person> people) {
        String sql = "INSERT INTO person(name, age, email, address) VALUES (?, ?, ?, ?)";

        try {
            int[][] updateCounts = jdbcTemplate.batchUpdate(sql, people, 1000, (ps, person) -> {
                ps.setString(1, person.getName());
                ps.setInt(2, person.getAge());
                ps.setString(3, person.getEmail());
                ps.setString(4, person.getAddress());
            });

            // Подсчёт общего количества вставленных строк
            int totalInserted = Arrays.stream(updateCounts)
                .flatMapToInt(IntStream::of)
                .sum();
            System.out.println("✅ Batch вставка выполнена успешно. Вставлено строк: " + totalInserted);

        } catch (DataAccessException e) {
            System.err.println("❌ Ошибка при batch вставке. Транзакция будет откатана.");
            throw e;
        }
    }

}

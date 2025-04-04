package com.example.demo.repository;

import com.example.demo.models.Person;
import com.example.demo.utils.PostgresConnector;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("jdbc")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonJdbcRepository implements PersonRepository {

    final PostgresConnector postgresConnector;

    public List<Person> findAll() {
        System.out.println("findAll in jdbc");
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection connection = postgresConnector.connect();
             Statement statement = connection.createStatement();  // Это тот обьект который содержит в себе запрос к БД
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                people.add(mapRow(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public Optional<Person> findById(long person_id) {
        String sql = "SELECT * FROM person WHERE person_id = ?";
        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, person_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return Optional.empty();
    }

    public void save(Person person) {
        String sql = "INSERT INTO person(name, age, email, address) VALUES (?, ?, ?, ?)";

        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getAddress());

            // никакие данные не возвращает с БД
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                person.setPerson_id(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Person updatedPerson) {
        String sql = "UPDATE person SET name = ?, age = ?, email = ?, address = ? WHERE person_id = ?";

        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, updatedPerson.getName());
            ps.setInt(2, updatedPerson.getAge());
            ps.setString(3, updatedPerson.getEmail());
            ps.setString(4, updatedPerson.getAddress());
            ps.setLong(5, updatedPerson.getPerson_id());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(long person_id) {
        String sql = "DELETE FROM person WHERE person_id = ?";

        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, person_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void butchSaveAll(List<Person> people) {
        // todo: метод здесь не работает
    }

    private Person mapRow(ResultSet rs) throws SQLException {
        return new Person(
            rs.getLong("person_id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("email"),
            rs.getString("address")
        );
    }
}

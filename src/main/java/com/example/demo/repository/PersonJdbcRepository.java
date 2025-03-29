package com.example.demo.repository;

import com.example.demo.models.Person;
import com.example.demo.utils.PostgresConnector;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PersonJdbcRepository {

    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection connection = PostgresConnector.connect();
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

    public Person show(int id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        try (Connection connection = PostgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Person person) {
        String sql = "INSERT INTO person(name, age, email, city) VALUES (?, ?, ?, ?)";

        try (Connection connection = PostgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getCity());

            // никакие данные не возвращает с БД
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                person.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Person updatedPerson) {
        if (updatedPerson.getId() == null || !Objects.equals(id, updatedPerson.getId())) {
            throw new IllegalArgumentException("ID mismatch or null");
        }

        String sql = "UPDATE person SET name = ?, age = ?, email = ?, city = ? WHERE id = ?";

        try (Connection connection = PostgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, updatedPerson.getName());
            ps.setInt(2, updatedPerson.getAge());
            ps.setString(3, updatedPerson.getEmail());
            ps.setString(4, updatedPerson.getCity());
            ps.setInt(5, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM person WHERE id = ?";

        try (Connection connection = PostgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Person mapRow(ResultSet rs) throws SQLException {
        return new Person(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("email"),
            rs.getString("city")
        );
    }
}

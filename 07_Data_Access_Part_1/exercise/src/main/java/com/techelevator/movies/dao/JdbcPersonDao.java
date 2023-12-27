package com.techelevator.movies.dao;

import com.techelevator.movies.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcPersonDao implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPersonDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Person> getPersons() {
        String sql = "SELECT * FROM Person";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person getPersonById(int id) {
        String sql = "SELECT * FROM Person WHERE person_id = ?";
        List<Person> persons = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), id);

        if (!persons.isEmpty()) {
            return persons.get(0); // Return the first person found (assuming person_id is unique)
        } else {
            return null; // Return null if the person with the specified ID is not found.
        }
    }


    @Override
    public List<Person> getPersonsByName(String name, boolean useWildCard) {
        String sql;
        if (useWildCard) {
            sql = "SELECT * FROM Person WHERE name LIKE ?";
            name = "%" + name + "%"; // Add wildcards to the search term
        } else {
            sql = "SELECT * FROM Person WHERE name = ?";
        }

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), name);
    }

    @Override
    public List<Person> getPersonsByCollectionName(String collectionName, boolean useWildCard) {
        String sql;
        if (useWildCard) {
            sql = "SELECT p.* FROM Person p JOIN Collection c ON p.collection_id = c.collection_id WHERE c.collection_name LIKE ?";
            collectionName = "%" + collectionName + "%"; // Add wildcards to the search term
        } else {
            sql = "SELECT p.* FROM Person p JOIN Collection c ON p.collection_id = c.collection_id WHERE c.collection_name = ?";
        }

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), collectionName);
    }

    }


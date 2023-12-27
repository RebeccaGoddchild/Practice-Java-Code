package com.techelevator.movies.dao;

import com.techelevator.movies.model.Collection;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCollectionDao implements CollectionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCollectionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Collection> getCollections() {
        return executeQuery("SELECT collection_id, collection_name FROM Collection");
    }

    @Override
    public Collection getCollectionById(int id) {
        String sql = "SELECT collection_id, collection_name FROM Collection WHERE collection_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Collection.class), id);
    }

    @Override
    public List<Collection> getCollectionsByName(String name, boolean useWildCard) {
        String sql;
        Object[] params;

        if (useWildCard) {
            sql = "SELECT collection_id, collection_name FROM Collection WHERE LOWER(collection_name) LIKE LOWER(?)";
            params = new Object[]{"%" + name + "%"}; // Add wildcards to the search term
        } else {
            sql = "SELECT collection_id, collection_name FROM Collection WHERE LOWER(collection_name) = LOWER(?)";
            params = new Object[]{name};
        }

        return executeQuery(sql, params);
    }

    private List<Collection> executeQuery(String sql, Object... params) {
        List<Collection> collections = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);

        while (results.next()) {
            int collectionId = results.getInt("collection_id");
            String collectionName = results.getString("collection_name");
            collections.add(new Collection(collectionId, collectionName));
        }

        return collections;
    }

    private Collection executeQueryForObject(String sql, Object... params) {
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, params);
        if (result.next()) {
            int collectionId = result.getInt("collection_id");
            String collectionName = result.getString("collection_name");
            return new Collection(collectionId, collectionName);
        }
        return null;
    }
}

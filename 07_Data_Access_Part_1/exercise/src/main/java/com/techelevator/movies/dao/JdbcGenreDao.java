package com.techelevator.movies.dao;

import com.techelevator.movies.model.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcGenreDao implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGenreDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Genre> getGenres() {
        String sql = "SELECT * FROM Genre";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class));
    }

    @Override
    public Genre getGenreById(int id) {
        String sql = "SELECT * FROM Genre WHERE genre_id = ?";

        try {
            Genre genre=null;
            System.out.println("Executing SQL: " + sql + " with ID: " + id);
           // Genre result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Genre.class), id);
            //System.out.println("Query result: " + result);
            //return result;
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                genre = mapRowToGenre(results);
            }
            return genre;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No result found for ID: " + id);
            return null;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Genre> getGenresByName(String name, boolean useWildCard) {
        List<Genre> genres = new ArrayList<>();
        String sql;
        Object[] params;

        if (useWildCard) {
            sql = "SELECT * FROM Genre WHERE LOWER(genre_name) LIKE LOWER(?)";
            params = new Object[]{"%" + name + "%"}; // Add wildcards to the search term
        } else {
            sql = "SELECT * FROM Genre WHERE LOWER(genre_name) = LOWER(?)";
            params = new Object[]{name};
        }

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);

        while (results.next()) {
            int genreId = results.getInt("genre_id");
            String genreName = results.getString("genre_name");
            genres.add(new Genre(genreId, genreName));
        }

        return genres;
    }
    private Genre mapRowToGenre(SqlRowSet sqlRowSet) {

        Genre genre = new Genre();
        genre.setId(sqlRowSet.getInt("genre_id"));
        genre.setName(sqlRowSet.getString("genre_name"));
        return genre;
    }
}


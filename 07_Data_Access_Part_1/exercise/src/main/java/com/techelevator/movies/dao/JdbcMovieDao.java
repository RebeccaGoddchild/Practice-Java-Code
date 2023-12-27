package com.techelevator.movies.dao;

import com.techelevator.movies.model.Genre;
import com.techelevator.movies.model.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcMovieDao implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Movie> getMovies() {
        String sql = "SELECT * FROM Movie";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public Movie getMovieById(int id) {
        String sql = "SELECT * FROM Movie WHERE genre_id = ?";

        try {
            Movie movie=null;
            System.out.println("Executing SQL: " + sql + " with ID: " + id);
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                movie = mapRowToMovie(results);
            }
            return movie;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No result found for ID: " + id);
            return null;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Movie> getMoviesByTitle(String name, boolean useWildCard) {
        List<Movie> movies = new ArrayList<>();
        String sql;
        Object[] params;

        if (useWildCard) {
            sql = "SELECT * FROM Movie WHERE LOWER(movie_title) LIKE LOWER(?)";
            params = new Object[]{"%" + name + "%"}; // Add wildcards to the search term
        } else {
            sql = "SELECT * FROM Movie WHERE LOWER(movie_title) = LOWER(?)";
            params = new Object[]{name};
        }

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, params);

        while (results.next()) {
            int movieId = results.getInt("movie_id");
            String movieTitle = results.getString("movie_title");
            String movieTagline = results.getString("tagline");
            String movieOverview = results.getString("overview");
            String moviePosterPath = results.getString("poster_path");
            String movieHomePage = results.getString("home_page");
            LocalDate movieReleaseDate = LocalDate.parse(results.getString("release_date"));
            int movieLengthInMinutes = results.getInt("length_minutes");
            int movieDirectorId = results.getInt("director_id");
            int movieCollectionId = results.getInt("collection_id");
            movies.add(new Movie(movieId, movieTitle, movieOverview, movieTagline, moviePosterPath, movieHomePage, movieReleaseDate, movieLengthInMinutes, movieDirectorId, movieCollectionId));
        }

        return movies;
    }


    @Override
    public List<Movie> getMoviesByDirectorNameBetweenYears(
            String directorName, int startYear, int endYear, boolean useWildCard) {
        String sql = useWildCard ?
                "SELECT DISTINCT m.* " +
                        "FROM movie m " +
                        "JOIN movie_director md ON m.movie_id = md.movie_id " +
                        "JOIN person p ON md.person_id = p.person_id " +
                        "WHERE LOWER(p.name) LIKE LOWER(?) " +
                        "AND m.release_year BETWEEN ? AND ?" :
                "SELECT DISTINCT m.* " +
                        "FROM movie m " +
                        "JOIN movie_director md ON m.movie_id = md.movie_id " +
                        "JOIN person p ON md.person_id = p.person_id " +
                        "WHERE p.name = ? " +
                        "AND m.release_year BETWEEN ? AND ?";
        List<Movie> movieList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class), useWildCard ? "%" + directorName + "%" : directorName, startYear, endYear);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Movie.class));
    }


    private Movie mapRowToMovie(SqlRowSet sqlRowSet) {
        Movie mappedMovie = new Movie();
        mappedMovie.setId(sqlRowSet.getInt("movie_id"));
        mappedMovie.setTitle(sqlRowSet.getString("title"));
        mappedMovie.setTagline(sqlRowSet.getString("tag_line"));
        mappedMovie.setPosterPath(sqlRowSet.getString("poster_path"));
        mappedMovie.setHomePage(sqlRowSet.getString("home_page"));
        mappedMovie.setReleaseDate(sqlRowSet.getDate("release_date").toLocalDate());
        mappedMovie.setLengthMinutes(sqlRowSet.getInt("legth_minutes"));
        mappedMovie.setDirectorId(sqlRowSet.getInt("director_id"));


        return mappedMovie;
    }
}






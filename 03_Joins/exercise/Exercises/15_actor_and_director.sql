-- 15. The title of the movie and the name of director for movies where the director was also an actor in the same movie.
-- Order the results by movie title (A-Z)
-- (73 rows)
SELECT title, person_name 
FROM movie
JOIN person ON movie.director_id = person.person_id
WHERE movie.movie_id IN (
  SELECT movie_id
  FROM movie_actor
  WHERE actor_id = movie.director_id
)
ORDER BY movie.title ASC;

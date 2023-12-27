-- 8. The genres of movies that Robert De Niro has appeared in that were released in 2010 or later, sorted alphabetically.
-- (6 rows)
SELECT DISTINCT genre.genre_name
FROM person AS p
JOIN movie_actor AS ma ON p.person_id = ma.actor_id
JOIN movie_genre AS mg ON ma.movie_id = mg.movie_id
JOIN genre ON mg.genre_id = genre.genre_id
JOIN movie AS m ON m.movie_id = mg.movie_id
WHERE person_name = 'Robert De Niro'
AND m.release_date >= '2010-01-01'
ORDER BY genre.genre_name ASC;

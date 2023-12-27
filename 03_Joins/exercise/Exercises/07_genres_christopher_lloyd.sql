-- 7. The genres of movies that Christopher Lloyd has appeared in, sorted alphabetically.
-- (8 rows) Hint: DISTINCT will prevent duplicate values in your query results.
SELECT DISTINCT genre.genre_name
FROM person AS p
JOIN movie_actor AS ma ON p.person_id = ma.actor_id
JOIN movie_genre AS mg ON ma.movie_id = mg.movie_id
JOIN genre ON mg.genre_id = genre.genre_id
WHERE person_name = 'Christopher Lloyd'
ORDER BY genre.genre_name ASC;

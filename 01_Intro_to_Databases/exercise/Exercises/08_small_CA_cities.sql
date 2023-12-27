-- 8. The name and population of cities in California (CA) with a population less than 150,000 people (37 rows)
SELECT city_name, population
FROM city
WHERE population < 150000 AND state_abbreviation='CA';

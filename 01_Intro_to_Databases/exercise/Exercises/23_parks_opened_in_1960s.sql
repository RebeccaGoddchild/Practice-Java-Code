-- 23. The name and date established of parks opened in the 1960s (6 rows)
SELECT park_name, date_established
FROM park
WHERE  EXTRACT(YEAR FROM date_established) BETWEEN 1960 AND 1969;;
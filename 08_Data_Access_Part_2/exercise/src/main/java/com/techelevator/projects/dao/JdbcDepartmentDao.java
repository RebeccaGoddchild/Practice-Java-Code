package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Department;

public class JdbcDepartmentDao implements DepartmentDao {

	private final String DEPARTMENT_SELECT = "SELECT d.department_id, d.name FROM department d ";
	
	private final JdbcTemplate jdbcTemplate;

	public JdbcDepartmentDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Department getDepartmentById(int id) {
		Department department = null;
		String sql = DEPARTMENT_SELECT +
				" WHERE d.department_id=?";
		try {
			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
			if (results.next()) {
				department = mapRowToDepartment(results);
			}
		}
		catch (EmptyResultDataAccessException ex) {
			// Handle the exception (e.g., log an error or provide a user-friendly message)
			System.err.println("Department not found with ID: " + id);
		}

		return department;
	}

	@Override
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<>();
		String sql = DEPARTMENT_SELECT;
		try {

			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				departments.add(mapRowToDepartment(results));
			}
		}
		catch  (DataAccessException ex) {
			// Handle the exception (e.g., log an error or provide user-friendly feedback)
			System.err.println("Error while fetching departments: " + ex.getMessage());
			// You might also consider re-throwing the exception or returning an empty list.
		}
		
		return departments;
	}

	@Override
	public Department createDepartment(Department department) {
		Department newDepartment = null; // Declare return value

		String insertSql = "INSERT INTO department (name) VALUES (?) RETURNING id";

		try {
			// Use a PreparedStatement to insert the department data and retrieve the new ID
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
				ps.setString(1, department.getName());
				return ps;
			}, keyHolder);

			// Retrieve the new department's ID
			int newDepartmentId = keyHolder.getKey().intValue();

			// Retrieve the new department to get any updated fields like timestamps
			newDepartment = getDepartmentById(newDepartmentId);

		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}

		return newDepartment;
	}

	@Override
	public Department updateDepartment(Department department) {
		Department updatedDepartment = null; // Declare return value

		String updateSql = "UPDATE department SET name = ? WHERE id = ?";

		try {
			int rowsAffected = jdbcTemplate.update(updateSql, department.getName(), department.getId());

			if (rowsAffected > 0) {
				// The update was successful
				updatedDepartment = getDepartmentById(department.getId()); // Retrieve the updated department
			} else {
				// Handle the case where no rows were updated (e.g., department not found)
				throw new DaoException("Department with ID " + department.getId() + " not found or not updated.");
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}

		return updatedDepartment;
	}


	@Override
	public int deleteDepartmentById(int id) {
		String deleteSql = "DELETE FROM department WHERE id = ?";

		try {
			int rowsAffected = jdbcTemplate.update(deleteSql, id);

			if (rowsAffected > 0) {
				// The deletion was successful
				return rowsAffected; // Return the number of rows affected (usually 1)
			} else {
				// Handle the case where no rows were deleted (e.g., department not found)
				throw new DaoException("Department with ID " + id + " not found or not deleted.");
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
	}

	private Department mapRowToDepartment(SqlRowSet results) {
		Department department = new Department();
		department.setId(results.getInt("department_id"));
		department.setName(results.getString("name"));
		return department;
	}

}

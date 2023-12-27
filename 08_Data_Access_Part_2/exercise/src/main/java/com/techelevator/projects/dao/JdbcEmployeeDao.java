package com.techelevator.projects.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Department;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Employee;

public class JdbcEmployeeDao implements EmployeeDao {

	private final String EMPLOYEE_SELECT = "SELECT e.employee_id, e.department_id, e.first_name, e.last_name, " +
				"e.birth_date, e.hire_date FROM employee e ";

	private final JdbcTemplate jdbcTemplate;

	public JdbcEmployeeDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Employee getEmployeeById(int id) {
		Employee employee = null;
		String sql = EMPLOYEE_SELECT +
				" WHERE e.employee_id=?";
		try {

			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
			if (results.next()) {
				employee = mapRowToEmployee(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}  catch (Exception e) {
		throw new DaoException("An error occurred while retrieving the employee", e);
	}

		return employee;
	}

	@Override
	public List<Employee> getEmployees() {
		List<Employee> allEmployees = new ArrayList<>();
		String sql = EMPLOYEE_SELECT;

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			Employee employeeResult = mapRowToEmployee(results);
			allEmployees.add(employeeResult);
		}

		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesByFirstNameLastName(String firstName, String lastName) {
		List<Employee> allEmployees = new ArrayList<>();
		String sql = EMPLOYEE_SELECT +
				" WHERE e.first_name ILIKE ? AND e.last_name ILIKE ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + firstName + "%", "%" + lastName + "%");
		while (results.next()) {
			Employee employeeResult = mapRowToEmployee(results);
			allEmployees.add(employeeResult);
		}

		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(int projectId) {
		List<Employee> allEmployees = new ArrayList<>();
		String sql =  EMPLOYEE_SELECT +
				"JOIN project_employee pe ON e.employee_id = pe.employee_id " +
				"WHERE pe.project_id = ?";
		try {

			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
			while (results.next()) {
				Employee employeeResult = mapRowToEmployee(results);
				allEmployees.add(employeeResult);
			}
		}catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		} catch (Exception e) {
			throw new DaoException("An error occurred while retrieving employees by project ID", e);
		}

		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> allEmployees = new ArrayList<>();
		String sql = EMPLOYEE_SELECT +
				" WHERE e.employee_id NOT IN (SELECT DISTINCT employee_id FROM project_employee)";
		try {

			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				Employee employeeResult = mapRowToEmployee(results);
				allEmployees.add(employeeResult);
			}
		}catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		} catch (Exception e) {
			throw new DaoException("An error occurred while retrieving employees without projects", e);
		}

		return allEmployees;
	}

	@Override
	public Employee createEmployee(Employee employee) {
		try {
			// Define the SQL INSERT statement
			String insertSql = "INSERT INTO employee (department_id, first_name, last_name, birth_date, hire_date) " +
					"VALUES (?, ?, ?, ?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();

			// Execute the SQL statement and retrieve the generated ID
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"employee_id"});
				ps.setLong(1, employee.getDepartmentId());
				ps.setString(2, employee.getFirstName());
				ps.setString(3, employee.getLastName());

                return ps;
            }, keyHolder);

			// Retrieve the generated employee ID
			Long generatedEmployeeId = keyHolder.getKey().longValue();


			return employee;
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw  new DaoException("Data integrity violation", e);
		}
	}


	
	@Override
	public Employee updateEmployee(Employee employee) {
		try {
			// Define the SQL UPDATE statement
			String updateSql = "UPDATE employee SET department_id = ?, first_name = ?, last_name = ?, " +
					"birth_date = ?, hire_date = ? WHERE employee_id = ?";

			// Execute the SQL UPDATE statement
			int rowsUpdated = jdbcTemplate.update(updateSql,
					employee.getDepartmentId(),
					employee.getFirstName(),
					employee.getLastName(),
					employee.getId());

			if (rowsUpdated > 0) {
				// The update was successful; retrieve the updated employee
				Employee updatedEmployee = getEmployeeById(employee.getId());
				return updatedEmployee;
			} else {
				// Handle the case where no rows were updated (e.g., employee not found)
				throw new DaoException("Employee with ID " + employee.getId() + " not found or not updated.");
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new  DaoException("Data integrity violation", e);
		}
	}


	@Override
	public int deleteEmployeeById(int id) {
		try {
			// Define the SQL DELETE statement
			String deleteSql = "DELETE FROM employee WHERE employee_id = ?";

			// Execute the SQL DELETE statement
			int rowsDeleted = jdbcTemplate.update(deleteSql, id);

			return rowsDeleted;
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
	}

	@Override
	public int deleteEmployeesByDepartmentId(int departmentId) {
		try {
			// Define the SQL DELETE statement
			String deleteSql = "DELETE FROM employee WHERE department_id = ?";

			// Execute the SQL DELETE statement
			int rowsDeleted = jdbcTemplate.update(deleteSql, departmentId);

			return rowsDeleted;
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
	}

	private Employee mapRowToEmployee(SqlRowSet result) {
		Employee employee = new Employee();
		employee.setId(result.getInt("employee_id"));
		employee.setDepartmentId(result.getInt("department_id"));
		employee.setFirstName(result.getString("first_name"));
		employee.setLastName(result.getString("last_name"));
		employee.setBirthDate(result.getDate("birth_date").toLocalDate());
		employee.setHireDate(result.getDate("hire_date").toLocalDate());
		return employee;
	}
}

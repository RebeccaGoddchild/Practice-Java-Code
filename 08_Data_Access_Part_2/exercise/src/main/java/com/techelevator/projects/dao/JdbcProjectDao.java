package com.techelevator.projects.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.techelevator.projects.exception.DaoException;
import com.techelevator.projects.model.Project;

public class JdbcProjectDao implements ProjectDao {

	private final String PROJECT_SELECT = "SELECT p.project_id, p.name, p.from_date, p.to_date FROM project p";

	private final JdbcTemplate jdbcTemplate;

	public JdbcProjectDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Project getProjectById(int projectId) {
		Project project = null;
		String sql = PROJECT_SELECT +
				" WHERE p.project_id=?";
		try {

			SqlRowSet results = jdbcTemplate.queryForRowSet(sql, projectId);
			if (results.next()) {
				project = mapRowToProject(results);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}

		return project;
	}

	@Override
	public List<Project> getProjects() {
		List<Project> allProjects = new ArrayList<>();
		String sql = PROJECT_SELECT;
		try {


			SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
			while (results.next()) {
				Project projectResult = mapRowToProject(results);
				allProjects.add(projectResult);
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}


		return allProjects;
	}

	@Override
	public Project createProject(Project newProject) {
		Project createdProject = null; // Declare the return value

		// Define the SQL INSERT statement
		String insertSql = "INSERT INTO project (name, from_date, to_date) VALUES (?, ?, ?) RETURNING project_id";

		try {
			// Execute the SQL INSERT statement and retrieve the generated project ID
			Long newProjectId = jdbcTemplate.queryForObject(insertSql, Long.class,
					newProject.getName());

			// Retrieve the new project to get any updated fields like timestamps
			createdProject = getProjectById(newProjectId.intValue());
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}

		return createdProject;
	}
	
	@Override
	public void linkProjectEmployee(int projectId, int employeeId) {
		try {
			// Define the SQL INSERT statement for linking a project to an employee
			String insertSql = "INSERT INTO project_employee (project_id, employee_id) VALUES (?, ?)";

			// Execute the SQL INSERT statement to create the link
			jdbcTemplate.update(insertSql, projectId, employeeId);
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
	}

	@Override
	public void unlinkProjectEmployee(int projectId, int employeeId) {
		try {
			// Define the SQL DELETE statement for unlinking an employee from a project
			String deleteSql = "DELETE FROM project_employee WHERE project_id = ? AND employee_id = ?";

			// Execute the SQL DELETE statement to remove the link
			jdbcTemplate.update(deleteSql, projectId, employeeId);
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
	}

	@Override
	public Project updateProject(Project project) {
		try {
			// Define the SQL UPDATE statement for updating a project
			String updateSql = "UPDATE project SET name = ?, from_date = ?, to_date = ? WHERE project_id = ?";

			// Execute the SQL UPDATE statement
			int rowsUpdated = jdbcTemplate.update(updateSql,
					project.getName(),
					project.getId());

			if (rowsUpdated > 0) {
				// The update was successful; retrieve the updated project
				Project updatedProject = getProjectById(project.getId());
				return updatedProject;
			} else {
				// Handle the case where no rows were updated (e.g., project not found)
				throw new DaoException("Project with ID " + project.getId() + " not found or not updated.");
			}
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
	}
	@Override
	public int deleteProjectById(int projectId) {
		try {
			// Define the SQL DELETE statement
			String deleteSql = "DELETE FROM project WHERE project_id = ?";

			// Execute the SQL DELETE statement
			int rowsDeleted = jdbcTemplate.update(deleteSql, projectId);

			return rowsDeleted;
		} catch (CannotGetJdbcConnectionException e) {
			throw new DaoException("Unable to connect to the server or database", e);
		} catch (DataIntegrityViolationException e) {
			throw new DaoException("Data integrity violation", e);
		}
	}

	
	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		project.setId(results.getInt("project_id"));
		project.setName(results.getString("name"));
		if (results.getDate("from_date") != null) {
			project.setFromDate(results.getDate("from_date").toLocalDate());
		}
		if (results.getDate("to_date") != null) {
			project.setToDate(results.getDate("to_date").toLocalDate());
		}
		return project;
	}

}

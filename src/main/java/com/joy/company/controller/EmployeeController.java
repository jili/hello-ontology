package com.joy.company.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joy.company.model.Employee;
import com.joy.company.repository.EmployeeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Employees API
 * 
 * @author Joy Li <joooy.li@gmail.com>
 */
@RestController
@Api(value = "Employees API", description = "REST API for employees", tags = { "Employees" })
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@ApiOperation(value = "Create an employee", tags = { "Employees" }, notes = "Authorized roles: <ul>"
			+ "<li>ADMIN</li></ul>")
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addEmployee(@Valid @RequestBody Employee employee) {
		String id = employeeRepository.save(employee);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Delete an employee", tags = { "Employees" }, notes = "Authorized roles: <ul>"
			+ "<li>ADMIN</li></ul>")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteEmployee(
			@ApiParam(value = "The UUID of the employee", name = "id", required = true) @PathVariable String id) {
		employeeRepository.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

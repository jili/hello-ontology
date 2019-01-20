package com.joy.company.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class Employee {
	private String id;

	@ApiModelProperty(value = "Firstname", required = true)
	@NotNull(message = "Firstname is required")
	private String firstname;

	@ApiModelProperty(value = "Lastname", required = true)
	@NotNull(message = "Lastname is required")
	private String surname;

	@ApiModelProperty(value = "The department that the employee belongs to")
	private Department department;

	public Employee() {
	}
	
	public Employee(String firstname, String surname, Department department) {
		this.firstname = firstname;
		this.surname = surname;
		this.department = department;
	}

	public String getId() {
		return id;
	}

	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}

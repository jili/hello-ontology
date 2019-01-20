package com.joy.company.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class Department {
	@ApiModelProperty(value = "The name of the department", required = true)
	@NotNull(message = "Department name is required")
	private String name;

	@ApiModelProperty(value = "The company that the department belongs to", required = true)
	@NotNull(message = "The company with a name is required")
	private Company company;

	public Department() {
	}

	public Department(String name, Company company) {
		this.name = name;
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@JsonIgnore
	public String getUriEnding() {
		return company.getName() + "/" + name;
	}
}

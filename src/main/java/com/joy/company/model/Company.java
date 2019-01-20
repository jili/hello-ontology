package com.joy.company.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class Company {
	@ApiModelProperty(value = "The name of the company", required = true)
	@NotNull(message = "Company name is required")
	private String name;

	@ApiModelProperty(value = "The primary business activities of the company")
	private String industry;

	@ApiModelProperty(value = "Refers to company's website")
	private String homepage;

	public Company() {
	}

	public Company(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
}

package com.joy.company;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joy.company.model.Company;
import com.joy.company.model.Department;
import com.joy.company.model.Employee;
import com.joy.company.repository.EmployeeRepository;
import com.joy.company.repository.RdfRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
public class EmployeeTests {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmployeeRepository repository;
	
	@MockBean
	private RdfRepository rdfRepository;

	/**
	 * Creating an employee successfully
	 */
	@Test
	public void createEmployee_201IsReceived() throws Exception {
		Employee bob = new Employee("Bob", "Johnson", new Department("Marketing", new Company("Migros")));
		mvc.perform(post("/employees").headers(createAuthHeader("admin", "admin"))
				.contentType(MediaType.APPLICATION_JSON).content(toJson(bob))).andExpect(status().isCreated());
		// TODO: get the new created employee, test its content and make sure it was
		// created correctly
	}

	/**
	 * Failed to create an employee due to 'unauthorized'
	 */
	@Test
	public void createEmployee_401IsReceived() throws Exception {
		Employee bob = new Employee("Bob", "Johnson", new Department("Marketing", new Company("Migros")));
		mvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(toJson(bob)))
				.andExpect(status().isUnauthorized());
	}

	/**
	 * Failed to create an employee due to 'no permission'
	 */
	@Test
	public void createEmployee_403IsReceived() throws Exception {
		Employee bob = new Employee("Bob", "Johnson", new Department("Marketing", new Company("Migros")));
		mvc.perform(post("/employees").headers(createAuthHeader("user", "password"))
				.contentType(MediaType.APPLICATION_JSON).content(toJson(bob))).andExpect(status().isForbidden());
	}

	/**
	 * Deleting an employee successfully
	 */
	@Test
	public void deleteEmployee_204IsReceived() throws Exception {
		Employee bob = new Employee("Bob", "Johnson", new Department("Marketing", new Company("Migros")));
		String id = repository.save(bob);
		mvc.perform(delete("/employees/" + id).headers(createAuthHeader("admin", "admin"))
				.contentType(MediaType.APPLICATION_JSON).content(toJson(bob))).andExpect(status().isNoContent());
		// TODO: test and make sure that the employee isn't there any more
	}

	@Test
	public void deleteEmployee_401IsReceived() throws Exception {
		Employee bob = new Employee("Bob", "Johnson", new Department("Marketing", new Company("Migros")));
		String id = repository.save(bob);
		mvc.perform(delete("/employees/" + id).contentType(MediaType.APPLICATION_JSON).content(toJson(bob)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void deleteEmployee_403IsReceived() throws Exception {
		Employee bob = new Employee("Bob", "Johnson", new Department("Marketing", new Company("Migros")));
		String id = repository.save(bob);
		mvc.perform(delete("/employees/" + id).headers(createAuthHeader("user", "password"))
				.contentType(MediaType.APPLICATION_JSON).content(toJson(bob))).andExpect(status().isForbidden());
	}

	private byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	private HttpHeaders createAuthHeader(String username, String password) {
		return new HttpHeaders() {
			private static final long serialVersionUID = -5286465252844247720L;

			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}
}

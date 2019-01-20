package com.joy.company.repository;

import java.io.File;
import java.util.UUID;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.springframework.stereotype.Repository;

import com.joy.company.Constants;
import com.joy.company.model.Employee;

@Repository
public class EmployeeRepository implements CustomRepository<Employee, String> {
	private org.eclipse.rdf4j.repository.Repository repo;

	public EmployeeRepository() {
		repo = new SailRepository(new NativeStore(new File(Constants.DATA_DIR)));
	}

	@Override
	public String save(Employee employee) {
		repo.initialize();
		String id = UUID.randomUUID().toString();
		employee.setId(id);
		Model model = buildModel(employee);
		try (RepositoryConnection conn = repo.getConnection()) {
			conn.add(model);
		} finally {
			repo.shutDown();
		}
		return id;
	}

	@Override
	public void delete(String id) {
		repo.initialize();
		IRI employee = repo.getValueFactory().createIRI(Constants.NAMESPACE, "employee/" + id);
		try (RepositoryConnection conn = repo.getConnection()) {
			conn.remove(conn.getStatements(employee, null, null));
		} finally {
			repo.shutDown();
		}
	}

	private Model buildModel(Employee employee) {
		ModelBuilder builder = new ModelBuilder();
		Model model = builder.setNamespace("ns", Constants.NAMESPACE).subject("ns:employee/" + employee.getId())
				.add(RDF.TYPE, "ns:Employee").add(FOAF.FIRST_NAME, employee.getFirstname())
				.add(FOAF.LAST_NAME, employee.getSurname())
				.add("ns:employeeOfDepartment", "ns:" + employee.getDepartment().getUriEnding()).build();
		return model;
	}
}
 
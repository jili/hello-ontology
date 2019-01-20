package com.joy.company.repository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.common.iteration.Iterations;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.springframework.stereotype.Repository;

import com.joy.company.Constants;

@Repository
public class RdfRepository {
	private org.eclipse.rdf4j.repository.Repository repo;

	public RdfRepository() {
		repo = new SailRepository(new NativeStore(new File(Constants.DATA_DIR)));
	}

	public List<String> getStatements() {
		repo.initialize();
		try (RepositoryConnection conn = repo.getConnection()) {
			try (RepositoryResult<Statement> result = conn.getStatements(null, null, null)) {
				List<String> stmts = Iterations.asList(result).stream().map(stmt -> stmt.toString())
						.collect(Collectors.toList());
				return stmts;
			}
		} finally {
			repo.shutDown();
		}
	}
	
	public List<Statement> getOntology() {
		repo.initialize();
		try (RepositoryConnection conn = repo.getConnection()) {
			try (RepositoryResult<Statement> result = conn.getStatements(null, null, null)) {
				return Iterations.asList(result);
			}
		} finally {
			repo.shutDown();
		}
	}
}

package com.joy.company.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joy.company.Constants;
import com.joy.company.repository.RdfRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Ontology API", description = "REST API for ontology information", tags = { "Ontology" })
@RequestMapping("/ontology")
public class RdfController {

	@Autowired
	private RdfRepository rdfRepository;

	@ApiOperation(value = "Returns all the RDF statements in response body", tags = {
			"Ontology" }, notes = "Authorized roles: <ul>" + "<li>ADMIN</li><li>USER</li></ul>")
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<List<String>> getStatements() {
		return new ResponseEntity<>(rdfRepository.getStatements(), HttpStatus.OK);
	}

	@ApiOperation(value = "Download a file with all the statements in turtle format", tags = {
			"Ontology" }, notes = "Authorized roles: <ul>" + "<li>ADMIN</li><li>USER</li></ul>")
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Resource> download(@RequestParam(defaultValue = "companies.ttl") String filename)
			throws Exception {
		String path = Constants.DATA_DIR + File.pathSeparator + filename;

		FileOutputStream out = new FileOutputStream(path);
		Rio.write(rdfRepository.getOntology(), out, RDFFormat.TURTLE);

		File file = new File(path);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
				.contentType(MediaType.parseMediaType("application/octet-stream")).contentLength(file.length())
				.body(resource);
	}
}

package org.lgangloff.starter.web.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.lgangloff.starter.domain.Authority;
import org.lgangloff.starter.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing User.
 */
@RestController
@RequestMapping("/api")
public class AuthorityResource {

	private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);
	
	@Autowired
	private AuthorityRepository authorityRepository;
    
	/**
	 * GET /authority -> get all the authority.
	 */
	@RequestMapping(value = "/authority", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getAll() throws URISyntaxException {
		log.debug("REST request to get all authority");
		return new ResponseEntity<>(authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList()), HttpStatus.OK);
	}
	
}

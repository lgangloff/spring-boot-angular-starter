package org.lgangloff.starter.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.lgangloff.starter.domain.Authority;
import org.lgangloff.starter.domain.User;
import org.lgangloff.starter.service.UserService;
import org.lgangloff.starter.web.rest.dto.UserDTO;
import org.lgangloff.starter.web.rest.util.HeaderUtil;
import org.lgangloff.starter.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing User.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	private UserService userService;
    
    
	/**
	 * POST /users -> Create a new user.
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO user) throws URISyntaxException {
		log.debug("REST request to save User : {}", user);
		if (user.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new user cannot already have an ID").body(null);
		}
		UserDTO result = UserDTO.MAPPER.apply(userService.doSave(user));
		return ResponseEntity.created(new URI("/api/user/" + result.getId()))
				.headers(HeaderUtil.createEntityUpdateAlert("user", user.getId().toString()))
				.body(result);
	}

	
	/**
	 * PUT /users -> Updates an existing user.
	 */

	@RequestMapping(value = "/users", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO user) throws URISyntaxException {
		log.debug("REST request to update User : {}", user);
		if (user.getId() == null) {
			return create(user);
		}
		UserDTO result = UserDTO.MAPPER.apply(userService.doSave(user));
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("user", user.getId().toString()))
				.body(result);
	}
		
	/**
	 * GET /users -> get all the users.
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserDTO>> getAll(
			@RequestParam(value = "page", required = false) Integer offset,
			@RequestParam(value = "per_page", required = false) Integer limit,
			@RequestParam(value = "search", required = false) String search) throws URISyntaxException {
		Pageable generatePageRequest = PaginationUtil.generatePageRequest(offset, limit);
		
		Page<User> page = doFindAll(generatePageRequest, search);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users", offset, limit);
		return new ResponseEntity<>(page.map(UserDTO.MAPPER).getContent(), headers, HttpStatus.OK);
	}
	
	
	protected Page<User> doFindAll(Pageable generatePageRequest, String search) {
		search = search == null ? "" :search;
		String customSearch = "%" + search.replaceAll("\\*", "%").toLowerCase() + "%";
		return userService.findAll(generatePageRequest);
	}

	/**
	 * GET /users/:id -> get the "id" user.
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> get(@PathVariable Long id) {
		log.debug("REST request to get User : {}", id);
		return Optional.ofNullable(doGet(id))
				.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	

	protected UserDTO doGet(Long id) {
		User user = userService.findOne(id);		
		UserDTO userDTO = UserDTO.MAPPER.apply(user);
		userDTO.setRoles(user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList()));
		return userDTO;
	}

	/**
	 * DELETE /users/:id -> delete the "id" user.
	 */

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.debug("REST request to delete User : {}", id);
		userService.deleteUser(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("user", id.toString())).build();
	}
	

	@RequestMapping(value = "/users/{id}/resetaccount", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> reset(@PathVariable Long id) {
		log.debug("REST request to reset User : {}", id);
		User user = userService.findOne(id);
		if (user != null){
			userService.initAccountAndSendMail(user);
		}
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/users/{id}/lock", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> lock(@PathVariable Long id) {
		log.debug("REST request to reset User : {}", id);
		User user = userService.findOne(id);
		if (user != null){
			userService.lockUser(user);
		}
		return ResponseEntity.ok().build();
	}
		
}

package com.aldidb.backenddb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aldidb.backenddb.message.RequestCreateOrganization;
import com.aldidb.backenddb.message.RequestRegisterUser;
import com.aldidb.backenddb.service.OrganizationService;
import com.aldidb.backenddb.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class Controller {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;

	
	// TODO Create controller for organization
	@PostMapping(path = "/register-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerUser(@RequestBody RequestRegisterUser request) {
		return userService.registerUser(request);
	}
	
	@PutMapping(path = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUser(@RequestBody RequestRegisterUser request) {
		return userService.updateUser(request);
	}

	@GetMapping(value = "/get-users")
	public ResponseEntity<Object> getAllUser() {
		return userService.getAllUser();
	}
	
	@GetMapping(value = "/get-user/{name}")
	public ResponseEntity<Object> getUser(@PathVariable("name") String name) {
		return userService.getUser(name);
	}
	
	@DeleteMapping(value = "/delete-user/{name}")
	public ResponseEntity<Object> deleteUser(@PathVariable("name") String name) {
		return userService.deleteUser(name);
	}
	
	// TODO Create controller for organization	
	@PostMapping(path = "/create-organization", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createOrganization(@RequestBody @Valid RequestCreateOrganization request) {
		return organizationService.createOrganization(request);
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> findBranch() {
		return ResponseEntity.ok().body("Morning guys");
	}
}

package com.aldidb.backenddb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aldidb.backenddb.message.BaseResponse;
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

	// TODO Create controller for user <==============================================================================================================================================>
	@PostMapping(path = "/login-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> loginUser(@RequestParam String username, @RequestParam String password) {
		return userService.loginUser(username, password);
	}

	@PostMapping(path = "/register-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registerUser(@RequestBody RequestRegisterUser request) {
		return userService.registerUser(request);
	}

	@PutMapping(path = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> updateUser(@RequestBody RequestRegisterUser request) {
		return userService.updateUser(request);
	}

	@GetMapping(value = "/get-users")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getAllUser() {
		return userService.getAllUser();
	}

	@GetMapping(value = "/get-user/{name}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getUser(@PathVariable("name") String name) {
		return userService.getUser(name);
	}

	@DeleteMapping(value = "/delete-user/{name}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> deleteUser(@PathVariable("name") String name) {
		return userService.deleteUser(name);
	}

	// TODO Create controller for organization <==============================================================================================================================================>
	@PostMapping(path = "/create-organization", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> createOrganization(@RequestBody @Valid RequestCreateOrganization request) {
		return organizationService.createOrganization(request);
	}

	@GetMapping(value = "/get-organization")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getOrganization(@RequestParam String uuiduser) {
		BaseResponse response = new BaseResponse();
		response.setMessage(BaseResponse.NOT_FOUND);
		if (uuiduser.isEmpty())
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		return organizationService.getOrganization(uuiduser);
	}

	@GetMapping(value = "/get-organizations")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> getOrganization() {
		return organizationService.getOrganization("");
	}

	@PutMapping(path = "/update-organization/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> updateOrganization(
			@PathVariable("uuid") String uuid,
			@RequestBody @Valid RequestCreateOrganization request) {
		return organizationService.updateOrganization(uuid, request);
	}

	@DeleteMapping(value = "/delete-organization/{uuid}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_SUPERVISOR')")
	public ResponseEntity<Object> deleteOrganization(@PathVariable("uuid") String uuid) {
		return organizationService.deleteOrganization(uuid);
	}
	
}

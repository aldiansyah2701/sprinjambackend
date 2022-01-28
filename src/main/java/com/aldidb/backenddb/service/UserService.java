package com.aldidb.backenddb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aldidb.backenddb.message.BaseResponse;
import com.aldidb.backenddb.message.RequestRegisterUser;
import com.aldidb.backenddb.message.ResponseGetAllUsers;
import com.aldidb.backenddb.model.Role;
import com.aldidb.backenddb.model.Role.ROLE;
import com.aldidb.backenddb.model.User;
import com.aldidb.backenddb.repository.ModelUserAndRoles;
import com.aldidb.backenddb.repository.RoleRepository;
import com.aldidb.backenddb.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public ResponseEntity<Object> loginUser(String userName, String password) {
		BaseResponse response = new BaseResponse();
		try {
			System.out.println(userName +  " " + password );
//			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			response.setMessage(BaseResponse.SUCCESS);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (AuthenticationException e) {
			response.setMessage(BaseResponse.FAILED);
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Transactional(readOnly = false)
	public ResponseEntity<Object> registerUser(RequestRegisterUser data) {
		BaseResponse response = new BaseResponse();
		User findByName = userRepository.findByName(data.getFullName());
		if (findByName != null) {
			response.setMessage(BaseResponse.ALREADY_EXIST);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		User user = new User();
		user.setName(data.getFullName());
		user.setPassword(passwordEncoder.encode(data.getPassword()));
		user.setCreatedDate(new Date());
		user.setActive(true);
		user = userRepository.save(user);

		for (String roleName : data.getRoles()) {
			Role role = new Role();
			role.setName(ROLE.valueOf(roleName).toString());
			role.setCreatedDate(new Date());
			role.setUser(user);
			role = roleRepository.save(role);
		}
		response.setMessage(BaseResponse.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Transactional(readOnly = false)
	public ResponseEntity<Object> updateUser(RequestRegisterUser data) {
		BaseResponse response = new BaseResponse();
		User user = userRepository.findByName(data.getFullName());

		if (user != null) {
			// TODO Update data user and then save
			user.setActive(true);
			user = userRepository.save(user);
			roleRepository.deleteByUser(user);
			for (String roleName : data.getRoles()) {
				Role role = new Role();
				role.setName(ROLE.valueOf(roleName).toString());
				role.setCreatedDate(new Date());
				role.setUser(user);
				role = roleRepository.save(role);
			}

			response.setMessage(BaseResponse.SUCCESS);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setMessage(BaseResponse.NOT_FOUND);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<Object> getAllUser() {

		List<ModelUserAndRoles> datas = roleRepository.getAllUserAndRoles();
		List<ResponseGetAllUsers> response = new ArrayList<>();

		for (ModelUserAndRoles data : datas) {
			ResponseGetAllUsers resp = new ResponseGetAllUsers();
			resp.setName(data.getName());
			resp.setActive(data.getActive() != 0 ? true : false);
			resp.setUuid(data.getUuid());
			resp.setRoles(Arrays.asList(data.getRoles().split(";")));
			response.add(resp);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getUser(String name) {

		ModelUserAndRoles data = roleRepository.getUserAndRoles(name);
		ResponseGetAllUsers resp = new ResponseGetAllUsers();
		resp.setName(data.getName());
		resp.setActive(data.getActive() != 0 ? true : false);
		resp.setUuid(data.getUuid());
		resp.setRoles(Arrays.asList(data.getRoles().split(";")));

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@Transactional(readOnly = false)
	public ResponseEntity<Object> deleteUser(String name) {
		BaseResponse response = new BaseResponse();
		User user = userRepository.findByName(name);

		if (user != null) {
			// TODO Update data user and then save
			user.setActive(false);
			user = userRepository.save(user);

			response.setMessage(BaseResponse.SUCCESS);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setMessage(BaseResponse.NOT_FOUND);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
}

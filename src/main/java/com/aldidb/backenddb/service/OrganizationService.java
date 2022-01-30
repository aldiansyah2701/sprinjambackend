package com.aldidb.backenddb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aldidb.backenddb.message.BaseResponse;
import com.aldidb.backenddb.message.RequestCreateOrganization;
import com.aldidb.backenddb.model.Organization;
import com.aldidb.backenddb.model.Organization.TYPE;
import com.aldidb.backenddb.model.User;
import com.aldidb.backenddb.repository.OrganizationRepository;
import com.aldidb.backenddb.repository.UserRepository;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = false)
	public ResponseEntity<Object> createOrganization(RequestCreateOrganization data) {
		BaseResponse response = new BaseResponse();
		User user = userRepository.findByName(data.getUserName());
		if (user == null) {
			response.setMessage(BaseResponse.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Organization org = organizationRepository.findByUserAndNameAndType(user, data.getName(), data.getType());
		if (org != null) {
			response.setMessage(BaseResponse.ALREADY_EXIST);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Organization organization = new Organization();
		organization.setCreatedDate(new Date());
		organization.setName(data.getName());
		organization.setType(TYPE.valueOf(data.getType()).toString());
		organization.setUser(user);
		organization = organizationRepository.save(organization);
		response.setMessage(BaseResponse.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getOrganization(String uuiduser) {
		List<Organization> data = new ArrayList<>();
		if ("".equals(uuiduser) || uuiduser.isEmpty()) {
			data = (List<Organization>) organizationRepository.findAll();
		} else {
			Optional<User> user = userRepository.findById(uuiduser);
			data = organizationRepository.findByUser(user.get());
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<Object> updateOrganization(String uuid, RequestCreateOrganization request) {
		BaseResponse response = new BaseResponse();
		Optional<Organization> org = organizationRepository.findById(uuid);
		if (org.get() == null) {
			response.setMessage(BaseResponse.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Organization orgUpdate = org.get();
		orgUpdate.setName(request.getName());
		orgUpdate.setType(TYPE.valueOf(request.getType()).toString());
		orgUpdate = organizationRepository.save(orgUpdate);
		response.setMessage(BaseResponse.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> deleteOrganization(String uuid) {
		BaseResponse response = new BaseResponse();
		try {
			organizationRepository.deleteById(uuid);
			response.setMessage(BaseResponse.SUCCESS);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}
}

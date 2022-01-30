package com.aldidb.backenddb.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aldidb.backenddb.model.Organization;
import com.aldidb.backenddb.model.User;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String>{
	
	Organization findByUserAndNameAndType(User user, String Name, String Type);
	
	List<Organization> findByUser(User user);

}

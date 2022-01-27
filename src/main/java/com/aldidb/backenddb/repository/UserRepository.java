package com.aldidb.backenddb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aldidb.backenddb.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, String>{
	User findByName(String name);
	

}

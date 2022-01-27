package com.aldidb.backenddb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aldidb.backenddb.model.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, String>{

}

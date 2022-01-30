package com.aldidb.backenddb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aldidb.backenddb.model.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {
	
	Currency findByName(String name);

}

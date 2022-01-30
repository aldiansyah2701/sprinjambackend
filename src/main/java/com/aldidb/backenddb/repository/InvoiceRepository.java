package com.aldidb.backenddb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aldidb.backenddb.model.Invoice;
import com.aldidb.backenddb.model.User;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String> {

	Invoice findByBusinessKey(String businessKey);
	
	Page<Invoice> findByUser(User user, Pageable pageable);
}

package com.aldidb.backenddb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aldidb.backenddb.model.Invoice;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String> {

}

package com.aldidb.backenddb.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aldidb.backenddb.message.BaseRequest;
import com.aldidb.backenddb.message.BaseResponse;
import com.aldidb.backenddb.model.Product;
import com.aldidb.backenddb.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = false)
	public ResponseEntity<Object> createProduct(BaseRequest request) {
		BaseResponse response = new BaseResponse();
		Product product = productRepository.findByName(request.getName());
		if (product != null) {
			response.setMessage(BaseResponse.ALREADY_EXIST);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Product data = new Product();
		data.setActive(true);
		data.setCreatedDate(new Date());
		data.setName(request.getName());
		data = productRepository.save(data);

		response.setMessage(BaseResponse.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getProducts() {
		List<Product> products = (List<Product>) productRepository.findAll();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	public ResponseEntity<Object> deleteProduct(String uuid) {
		BaseResponse response = new BaseResponse();

		try {
			Optional<Product> product = productRepository.findById(uuid);
			Product productDelete = product.get();
			productDelete.setActive(false);
			productDelete = productRepository.save(productDelete);
			response.setMessage(BaseResponse.SUCCESS);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {

			response.setMessage(BaseResponse.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		}
	}
}

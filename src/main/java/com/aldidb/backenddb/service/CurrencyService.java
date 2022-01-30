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
import com.aldidb.backenddb.model.Currency;
import com.aldidb.backenddb.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Transactional(readOnly = false)
	public ResponseEntity<Object> createCurrency(BaseRequest request) {
		BaseResponse response = new BaseResponse();
		Currency currency = currencyRepository.findByName(request.getName());
		if (currency != null) {
			response.setMessage(BaseResponse.ALREADY_EXIST);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Currency data = new Currency();
		data.setActive(true);
		data.setCreatedDate(new Date());
		data.setName(request.getName());
		data = currencyRepository.save(data);
		response.setMessage(BaseResponse.SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getCurrency() {
		List<Currency> currencys = (List<Currency>) currencyRepository.findAll();
		return new ResponseEntity<>(currencys, HttpStatus.OK);
	}

	public ResponseEntity<Object> deleteCurrency(String uuid) {
		BaseResponse response = new BaseResponse();
		try {
			Optional<Currency> currency = currencyRepository.findById(uuid);
			Currency currencyDelete = currency.get();
			currencyDelete.setActive(false);
			currencyDelete = currencyRepository.save(currencyDelete);
			response.setMessage(BaseResponse.SUCCESS);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setMessage(BaseResponse.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

	}

}

package com.aldidb.backenddb.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aldidb.backenddb.message.BaseResponse;
import com.aldidb.backenddb.message.ResponseApiException;

@ControllerAdvice
public class CustomErrorHandler extends ResponseEntityExceptionHandler {

	// TODO exception handle for request body
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = new ArrayList<String>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + " -> " + error.getDefaultMessage());
		}

		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + " -> " + error.getDefaultMessage());
		}

		ResponseApiException response = new ResponseApiException();
		response.setMessage(ex.getMessage());
		response.setErrors(errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(value = CustomGenericException.class)
	public ResponseEntity<Object> handleCustomException(CustomGenericException ex, HttpServletRequest request) {
		BaseResponse response = new BaseResponse();
		response.setMessage(ex.getMessage());
		Map<String, Object> resEx = new HashMap<String, Object>();
		resEx.put("status", ex.getStatus());
		resEx.put("errorNumber", ex.getError());
		resEx.put("message", ex.getMessage());
		return new ResponseEntity<>(resEx, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}

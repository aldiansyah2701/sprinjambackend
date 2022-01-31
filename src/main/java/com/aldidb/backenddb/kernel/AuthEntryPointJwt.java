package com.aldidb.backenddb.kernel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.aldidb.backenddb.message.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	// TODO exception handle for authentication
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		System.out.println(authException.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage(authException.getMessage());
		response.getWriter().write(convertObjectToJson(baseResponse));
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException accessDeniedException) throws IOException {
		httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setMessage(accessDeniedException.getMessage());
		httpServletResponse.getWriter().write(convertObjectToJson(baseResponse));
	}

	public String convertObjectToJson(Object object) throws JsonProcessingException {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

}

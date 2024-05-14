package com.dip.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> argumentErrorHandler(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			map.put(error.getField(), error.getDefaultMessage());
		});
		return map;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadCredentialsException.class)
	public Map<String, String> invalidCredentialsExceptionHandler(BadCredentialsException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(UserAlreadyExistsException.class)
	public Map<String, String> userAlreadyExistsExceptionHandler(UserAlreadyExistsException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("ErrorMessage", ex.getMessage());
		return map;
	}

}

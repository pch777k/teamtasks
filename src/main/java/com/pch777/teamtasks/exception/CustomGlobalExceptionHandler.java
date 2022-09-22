package com.pch777.teamtasks.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	    Map<String, Object> body = new LinkedHashMap<>();
	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    body.put("timestamp", new Date());
	    body.put("status", status.value());
	    List<String> errors = ex
	        .getBindingResult()
	        .getFieldErrors()
	        .stream()
	        .map(x -> x.getField() + " - " + x.getDefaultMessage())
	        .toList();
	    body.put("errors", errors);
	    return new ResponseEntity<>(body, status);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
	    Map<String, Object> body = new LinkedHashMap<>();
	    HttpStatus status = HttpStatus.NOT_FOUND;
	    body.put("timestamp", new Date());
	    body.put("status", status.value());
	    body.put("errors", ex.getMessage());
	    return new ResponseEntity<>(body, status);
	}
}

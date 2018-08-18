package org.rajnegi.spring.rest.webservices.springrestservice.exceptions;

import java.util.Date;

import org.rajnegi.spring.rest.webservices.springrestservice.resources.PostNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppWideExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value=Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
		ExceptionResponse response = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value=UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException uex){
		ExceptionResponse response = new ExceptionResponse(new Date(), uex.getMessage(), "UserResource Not Found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=PostNotFoundException.class)
	public final ResponseEntity<Object> handlePostNotFoundException(PostNotFoundException uex){
		ExceptionResponse response = new ExceptionResponse(new Date(), uex.getMessage(), "Post Not Found");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionResponse response = new ExceptionResponse(new Date(), "Validation failed", ex.getBindingResult().toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}

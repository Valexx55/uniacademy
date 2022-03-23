package edu.unisys.academy.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//con esta anotación, estoy centralizando la gestión de excepciones en esta clase
//es como "catch global"
@RestControllerAdvice (basePackages = {"edu.unisys.academy"})
public class GestionExcepciones {
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> errorDeBorrado (EmptyResultDataAccessException exception)
	{
		ResponseEntity<?> responseEntity = null;
		String str_exception = null;
		
			str_exception = "ERROR detectado  = " +exception.toString();
			responseEntity = ResponseEntity.internalServerError().body(str_exception);
			
			
		return responseEntity;
	}

}

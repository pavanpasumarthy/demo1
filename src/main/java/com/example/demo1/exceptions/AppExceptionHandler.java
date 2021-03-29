package com.example.demo1.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.demo1.ui.model.Response.ErrorMessage;

@ControllerAdvice
public class AppExceptionHandler {
	@ExceptionHandler(value = {UserServiceException.class})
  public ResponseEntity<Object> hancleUserServiceException(UserServiceException use,WebRequest wr)
{
		ErrorMessage errormeassage= new ErrorMessage(new Date(),use.getMessage());
		
				
return new ResponseEntity<>(errormeassage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);	  
}}

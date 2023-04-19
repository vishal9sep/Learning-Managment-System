package com.masai.team4.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public class NoHandlerFoundExceptions extends RuntimeException{
	
	private String message;


}

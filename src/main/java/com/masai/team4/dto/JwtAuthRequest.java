package com.masai.team4.dto;
import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username;
	
	private String password;
}

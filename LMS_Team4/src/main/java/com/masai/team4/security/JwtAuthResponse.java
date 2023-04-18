package com.masai.team4.security;
import com.masai.team4.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

	private String token;
	private UserDto user;
}

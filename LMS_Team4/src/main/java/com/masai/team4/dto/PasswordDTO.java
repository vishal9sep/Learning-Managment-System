package com.masai.team4.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class PasswordDTO {

	private String email;
	private String password;
	private String confirmPassword;
}

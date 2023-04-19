package com.masai.team4.dto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.masai.team4.entities.Batch;
import com.masai.team4.entities.Lectures;
import com.masai.team4.entities.Section;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 characters !!")
	private String name;

	@NotEmpty(message = "Email is required !!")
	@Email(message = "Email address is not valid")
	private String email;
	@NotEmpty
	@Size(min = 3, max = 8, message = "password must be min of 3 chars and max of 8 chars !!")
	private String password;

	private Batch batch;

	private Section section;

	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}

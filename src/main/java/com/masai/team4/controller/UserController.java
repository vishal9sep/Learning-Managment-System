package com.masai.team4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.team4.dto.UserDto;
import com.masai.team4.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// create user ------->
	
	@PostMapping("/")
	public ResponseEntity<UserDto> CreateUser(@RequestBody UserDto userDto ){
		UserDto createUserDto= this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createUserDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/adminlist")
	public ResponseEntity<List<UserDto>> getAdmins(){
		List<UserDto> ans = this.userService.getAdminUser();
		return new ResponseEntity<List<UserDto>>(ans, HttpStatus.OK);
	}

}

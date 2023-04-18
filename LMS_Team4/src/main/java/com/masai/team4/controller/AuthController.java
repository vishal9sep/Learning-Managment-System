package com.masai.team4.controller;

import com.masai.team4.dto.UserDto;
import com.masai.team4.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.team4.dto.JwtAuthRequest;
import com.masai.team4.exception.ApiException;
import com.masai.team4.security.JwtAuthResponse;
import com.masai.team4.security.JwtTokenHelper;
import com.masai.team4.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request

	) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String generateToken = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(generateToken);
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			System.out.println("invalid details !! ");
			throw new ApiException("invalid username password");
		}

	}

	@PostMapping("/signup/admin")
	public ResponseEntity<UserDto> registerAdmin(@RequestBody UserDto userDto) {
		UserDto registerAdmin = this.userService.registerAdmin(userDto);

		return new ResponseEntity<UserDto>(registerAdmin, HttpStatus.CREATED);

	}

	@PostMapping("/signup/student/{batchId}/{sectionId}")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto , @PathVariable Integer batchId , @PathVariable Integer sectionId) {

		UserDto registerNewUser = this.userService.registerStudent(userDto, batchId , sectionId);

		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);

	}



}

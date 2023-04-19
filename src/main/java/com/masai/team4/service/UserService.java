package com.masai.team4.service;

import java.util.List;

import com.masai.team4.dto.UserDto;

public interface UserService {

	UserDto registerAdmin(UserDto user);

	UserDto registerStudent(UserDto user, Integer sectionId, Integer batchId);

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	List<UserDto> getAdminUser();

	void deleteUser(Integer userID);

}
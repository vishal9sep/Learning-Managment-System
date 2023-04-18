package com.masai.team4.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.masai.team4.config.AppConstants;
import com.masai.team4.dto.UserDto;
import com.masai.team4.entities.Batch;
import com.masai.team4.entities.Role;
import com.masai.team4.entities.Section;
import com.masai.team4.entities.User;
import com.masai.team4.exception.ResourceNotFoundException;
import com.masai.team4.repository.BatchRepo;
import com.masai.team4.repository.RoleRepo;
import com.masai.team4.repository.SectionRepo;
import com.masai.team4.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private SectionRepo sectionrepo;
	
	@Autowired
	private BatchRepo batchrepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerAdmin(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}
	
	@Override
	public UserDto registerStudent(UserDto userDto, Integer sectionId , Integer batchId) {
		// TODO Auto-generated method stub
		
		Section section = this.sectionrepo.findById(sectionId)
				.orElseThrow(() -> new ResourceNotFoundException("section", "section id", sectionId));

		Batch batch = this.batchrepo.findById(batchId)
				.orElseThrow(() -> new ResourceNotFoundException("batch", "batch id", batchId));

		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.STUDENT_USER).get();
		user.getRoles().add(role);
		user.setBatch(batch);
		user.setSection(section);
		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		User savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto user, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Integer userID) {
		// TODO Auto-generated method stub

	}
	public Map<Integer, User> userMap() {
		
	    Map<Integer, User> map = new HashMap<>();
	    userRepo.findAll().forEach(c -> map.put(c.getId(), c));
	    return map;
	}

	@Override
	public List<UserDto> getAdminUser() {
		
		List<User> users = this.userRepo.findAll();
		Role role = this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		List<User> adminUsers = new ArrayList<User>();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getRoles().contains(role)) {
				adminUsers.add(users.get(i));
			}

		}

		List<UserDto> UserDtos = adminUsers.stream().map(user -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return UserDtos;

	}

}

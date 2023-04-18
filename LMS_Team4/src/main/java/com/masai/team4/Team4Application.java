package com.masai.team4;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.masai.team4.config.AppConstants;
import com.masai.team4.entities.Role;
import com.masai.team4.repository.RoleRepo;

@SpringBootApplication
@ComponentScan(basePackages = "com.masai.team4")
@Configuration
@EnableJpaRepositories(basePackages = "com.masai.team4.repository")
@EnableWebMvc
public class Team4Application {

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(Team4Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public void run(String... args) throws Exception {

		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");

			Role role1 = new Role();
			role1.setId(AppConstants.STUDENT_USER);
			role1.setName("STUDENT_USER");

			ArrayList<Role> roles = new ArrayList<Role>();
			roles.add(role);
			roles.add(role1);

			List<Role> result = this.roleRepo.saveAll(roles);
			
			System.out.println("LMS has Started.");

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

}

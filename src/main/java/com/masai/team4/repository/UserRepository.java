package com.masai.team4.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.team4.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);
}


package com.masai.team4.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.masai.team4.entities.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Integer> {

	PasswordResetToken findByResetToken(String token);
	@Modifying
	@Transactional
	@Query(value="DELETE FROM password_reset_token WHERE reset_token =?1", nativeQuery = true)
	void deleteByToken(String token);
}

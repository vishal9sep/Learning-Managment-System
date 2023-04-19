package com.masai.team4.entities;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PasswordResetToken {

	 @Id
	 @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="passwordresettoken_sequence")
	 private Long id;

	 @Column(name="resetToken")
	 private String resetToken;

	 @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	 @JoinColumn(nullable = false, name = "user_id")
	 private User user;

	 private LocalDateTime expiryDate;




}

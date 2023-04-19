package com.masai.team4.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.team4.entities.PasswordResetToken;
import com.masai.team4.entities.User;
import com.masai.team4.exception.InvalidTokenException;
import com.masai.team4.exception.TokenExpiredException;
import com.masai.team4.repository.PasswordResetTokenRepo;
import com.masai.team4.repository.UserRepository;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{
	 @Autowired
	  private UserRepository userRepository;
	  
	  @Autowired
	  private PasswordResetTokenRepo passwordresettoken;
	  @Autowired
	  private ResetPasswordService resetPasswordEmailSender;	
	
	  @Override
		public String processForgotPassword(String email) throws MessagingException {
		  User user = userRepository.findByEmail(email);
	        long currentTime = System.currentTimeMillis();
	        if (user == null) {
	            throw new IllegalArgumentException("No user found with email " + email);
	        }
	       
	        long timestamp = System.currentTimeMillis();
	        Instant instant = Instant.ofEpochMilli(timestamp);
	        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	        String token = UUID.randomUUID().toString();
	        
	        
	        PasswordResetToken tookentable = new PasswordResetToken();
	        tookentable.setResetToken(token);
	        tookentable.setUser(user);
	        tookentable.setExpiryDate(localDateTime);
	        passwordresettoken.save(tookentable);

	        String resetUrl = "http://localhost:3000/api/reset-password/" + token + "/" + email + "/" + timestamp;
	        
	        resetPasswordEmailSender.sendResetEmail(user.getEmail(), resetUrl);

	        return "We have emailed your password reset link!";
		}

	@Override
	public void verifyResetToken(String token, long timestamp) throws InvalidTokenException, TokenExpiredException {
		PasswordResetToken resetToken = passwordresettoken.findByResetToken(token);
	    if (resetToken == null) {
	        throw new InvalidTokenException();
	    }
	    long expirationTime = timestamp + (60*60 * 1000);
	    
	    if (System.currentTimeMillis() > expirationTime) {
	        throw new TokenExpiredException();
	    }
		
	}

	@Override
	public void deleteExpiredToken(String token) {
		passwordresettoken.deleteByToken(token);
	}
}

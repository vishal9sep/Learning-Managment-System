package com.masai.team4.service;

import javax.mail.MessagingException;

import com.masai.team4.exception.InvalidTokenException;
import com.masai.team4.exception.TokenExpiredException;



public interface ForgotPasswordService {
	public String processForgotPassword(String email)throws MessagingException;
	public void verifyResetToken(String token, long timestamp) throws InvalidTokenException, TokenExpiredException ;
	public void deleteExpiredToken(String token);
}

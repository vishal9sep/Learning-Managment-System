package com.masai.team4.service;

import javax.mail.MessagingException;

import com.masai.team4.entities.User;

public interface ResetPasswordService {
	
	public void sendResetEmail(String email, String resetUrl)throws MessagingException;
}

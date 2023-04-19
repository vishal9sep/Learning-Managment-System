package com.masai.team4.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.masai.team4.entities.PasswordResetToken;
import com.masai.team4.entities.User;
import com.masai.team4.repository.PasswordResetTokenRepo;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService{
	

	@Autowired
    private JavaMailSender emailSender;
	
	

	
	@Override
	public void sendResetEmail(String email, String resetUrl) throws MessagingException {
		
		MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setFrom("noreply@masaischool.com");
        helper.setSubject("Reset Password Notification");
	    String content=
	    		"<div style='background-color: #EAF1F2;'>"
	    		+"<h2 style='font-family: Arial, Helvetica, sans-serif; text-align: center;padding-top: 3%;margin-bottom: 2%;'>Masai School | Course</h2>"
	    		+"<div style='background-color:	#FFFFFF;  margin-right: 20%;margin-left: 20%; margin-bottom:0;'>"
	    		+"<h2 style='font-family: Arial, Helvetica, sans-serif; margin-left:5%; padding-top:4%; margin-bottom:4%;font-size: large; '>Hello!</h2>"
	    		+"<p style='font-size: 15px;font-family: Arial, Helvetica, sans-serif; color:#909090; margin-top: 4%; margin-right: 5%;margin-bottom: 4%;margin-left: 5%;'>You are receiving this email because we received a password reset request for your account.</p>" 
	    		+ "<button style=\"display:block; height:40px;margin:auto; background-color:black; border-radius: 7px; color:white;\"> <a href=\"" +resetUrl + "\"   style=\"display: inline-block; background-color: black; color: white; padding: 10px 20px; text-align: center; text-decoration: none;\">Reset Password</a></button>"
	    		+"<p style='font-size: 15px; font-family: Arial, Helvetica, sans-serif; color:#909090; margin-top: 4%; margin-right: 5%;margin-bottom: 4%;margin-left: 5%;'>This password reset link will expire in 60 minutes.</p>"
	    		+"<p style='font-size: 15px;font-family: Arial, Helvetica, sans-serif; color:#909090;margin-top: 4%; margin-right: 5%;margin-bottom: 4%;margin-left: 5%;'>If you did not request a password reset, no further action is required.</p>"
	    		+""
	    		+""
	    		+"<p style=' font-size: 15px;font-family: Arial, Helvetica, sans-serif; color:#909090; margin-top: 4%; margin-right: 5%;margin-bottom: 4%;margin-left: 5%;'>Regards,<br>Masai School | Course</p>"
	    		+""
	    		+"<hr style='border:none; border-top: 1px solid #ccc; margin-top: 20px; margin-bottom: 20px; margin-left:5%;margin-right:5%;'>"
	    		+ "<p style=' font-size: 13px;font-family: Arial, Helvetica, sans-serif; color:#909090;  margin-top: 4%; margin-right: 5%;margin-bottom: 4%;margin-left: 5%; padding-bottom:4%;'>\r\n"
	    		+ "If you're having trouble clicking the \"Reset Password\" button, copy and paste the URL below into your web browser: <a href=\"" + resetUrl + "\">" + resetUrl + "</a></p>"
	    		+"</div>"
	    		+"<div style='padding-bottom:15px;'>"
	    		+"<p style='font-family: Arial, Helvetica, sans-serif; color:#909090; text-align: center;' >© 2023 Masai School | Course. All rights reserved.</p>"
	    		+"</div>"
	    		+"</div>";
	    
	    helper.setText(content, true);
	    //message.setText("Click the following link to reset your password: " + resetUrl);
	    emailSender.send(message);
	}
	
}

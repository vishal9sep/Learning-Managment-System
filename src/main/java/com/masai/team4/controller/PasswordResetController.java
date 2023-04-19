package com.masai.team4.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.team4.dto.PasswordDTO;
import com.masai.team4.entities.PasswordResetToken;
import com.masai.team4.entities.User;
import com.masai.team4.exception.InvalidTokenException;
import com.masai.team4.exception.TokenExpiredException;
import com.masai.team4.repository.PasswordResetTokenRepo;
import com.masai.team4.repository.UserRepository;
import com.masai.team4.service.ForgotPasswordService;
import com.masai.team4.service.ResetPasswordService;


@RestController
@RequestMapping("/api")
public class PasswordResetController {
	@Autowired
    private UserRepository userRepository;

	@Autowired
    private PasswordResetTokenRepo passwordresettoken;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private ResetPasswordService resetpassword;

	@Autowired
	private ForgotPasswordService forgotpassword;

	@PostMapping("/forgot-password")
	public ResponseEntity<String> processForgotPassword(@RequestBody  PasswordDTO emailDTO) throws MessagingException {
	    String message = forgotpassword.processForgotPassword(emailDTO.getEmail());
	    return ResponseEntity.ok(message);
	}



	@GetMapping("/reset-password/{token}/{email}/{timestamp}")
	public Boolean showResetPasswordForm(@PathVariable("token") String token, @PathVariable("email") String email,@PathVariable("timestamp") long timestamp, Model model) {
			try {
				forgotpassword.verifyResetToken(token, timestamp);
				return true;
			} catch (TokenExpiredException e) {
				forgotpassword.deleteExpiredToken(token);
			return false;

			} catch (InvalidTokenException e) {
				forgotpassword.deleteExpiredToken(token);
				return false;

			}


	}

	@PostMapping("/reset-password/{token}")
	public ResponseEntity<String> processResetPassword(@PathVariable("token") String token, @RequestBody PasswordDTO passworddto) {
		PasswordResetToken check = passwordresettoken.findByResetToken(token);
		String password=passworddto.getPassword();
		String confirmPassword=passworddto.getConfirmPassword();
	    if (check == null) {
	        return new ResponseEntity<String>("Invalid or expired password reset token.", HttpStatus.UNAUTHORIZED);
	    }

	    if (!password.equals(confirmPassword)) {

	        return new ResponseEntity<String>("Passwords do not match.", HttpStatus.UNAUTHORIZED);
	    }
	   User user =check.getUser();
	    user.setPassword(this.passwordEncoder.encode(password));
	    passwordresettoken.deleteByToken(token);
	    userRepository.save(user);

	    return new ResponseEntity<String>("Reset Password Successfully", HttpStatus.ACCEPTED);
	}


}

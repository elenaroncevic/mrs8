package app.service;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import app.model.RegisteredUser;
import app.model.User;
import app.model.VerificationToken;
import app.repository.UserRepository;
import app.repository.VerificationTokenRepository;

@Service
public class RegistrationService {

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private VerificationTokenRepository tokenRepository;
  
    @Autowired
    private JavaMailSender mailSender;
	
	
	public boolean registration(String email, String pass, String firstName, String lastName, String city, String phone, WebRequest request) {
        String appUrl = request.getContextPath();
        if(userRepository.findOne(email)!=null) {
        	return false;
        }
        RegisteredUser ru = new RegisteredUser();
        ru.setEmail(email);
        ru.setPassword(pass);
        ru.setFirstName(firstName);
        ru.setLastName(lastName);
        ru.setCity(city);
        ru.setPhone(phone);
        ru.setActivated("no");
        userRepository.save(ru);
        
        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken(token, ru);
        tokenRepository.save(myToken);
        String subject = "Registration Confirmation";
        String confirmationUrl = appUrl + "/registrationConfirm.html?token=" + token;
        
        SimpleMailMessage eMail = new SimpleMailMessage();
        eMail.setTo(email);
        eMail.setSubject(subject);
        eMail.setText("http://localhost:8181" + confirmationUrl);
        mailSender.send(eMail);
        
		return true;
	}
	
	public boolean confirmRegistration(String token) {
	    VerificationToken verificationToken = tokenRepository.findByToken(token);
	    User user = tokenRepository.findByToken(token).getUser();
	    Calendar cal = Calendar.getInstance();
	    if(verificationToken==null || (verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	tokenRepository.delete(verificationToken.getId());
	    	return false;
	    }
	    user.setActivated("yes"); 
	    userRepository.save(user);
	    tokenRepository.delete(verificationToken.getId());
	    return true;
	}
}

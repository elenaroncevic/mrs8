package app.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private MessageSource messages;
  
    //@Autowired
    //private JavaMailSender mailSender;
	
	
	public boolean registration(String email, String pass, String firstName, String lastName, String city, String phone, WebRequest request) {
        String appUrl = request.getContextPath();

        RegisteredUser ru = new RegisteredUser();
        ru.setEmail(email);
        ru.setPassword(pass);
        ru.setFirstName(firstName);
        ru.setLastName(lastName);
        ru.setCity(city);
        ru.setPhone(phone);
        ru.setActivated("no");
        
        String token = UUID.randomUUID().toString();
        createVerificationToken(ru, token);
        String subject = "Registration Confirmation";
        String confirmationUrl = appUrl + "/registrationConfirm.html?token=" + token;
        String message = messages.getMessage("message.regSucc", null, request.getLocale());
        /*
        SimpleMailMessage eMail = new SimpleMailMessage();
        eMail.setTo(email);
        eMail.setSubject(subject);
        eMail.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(eMail);
        */
        System.out.println("sad cu da sacuvam "+ru.getEmail());
		userRepository.save(ru);
		System.out.println("sacuvan");
		return true;
	}
     
    public RegisteredUser getUser(String verificationToken) {
        RegisteredUser user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
     
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
     
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }
     
    public void createVerificationToken(RegisteredUser user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}

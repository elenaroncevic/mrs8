package app.service;

import java.net.URI;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import app.model.CinemaAdmin;
import app.model.FanZoneAdmin;
import app.model.RegisteredUser;
import app.model.SystemAdmin;
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
	
	public boolean registration(String email, String pass, String firstName, String lastName, String city, String phone, HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length());
		base=base+  "/";

        RegisteredUser ru = (RegisteredUser) userRepository.findOne(email);
        if(ru!=null) {
        	VerificationToken vt = tokenRepository.findByUser(ru);
        	Calendar cal = Calendar.getInstance();
        	Calendar now = Calendar.getInstance();
        	cal.setTime(vt.getExpiryDate());
        	
        	if(now.before(cal)){
        		return false;
        	}else {
        		tokenRepository.delete(vt);
        	}
        }else {
        	ru=new RegisteredUser();
        }
        ru.setEmail(email);
        ru.setPassword(pass);
        ru.setFirstName(firstName);
        ru.setLastName(lastName);
        ru.setCity(city);
        ru.setPhone(phone);
        ru.setActivated("no");
        userRepository.save(ru);
        
        
        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUser(ru);
        tokenRepository.save(myToken);
        String subject = "Registration Confirmation";
        String confirmationUrl = base + "registrationConfirm.html?token=" + token;
        
        
        SimpleMailMessage eMail = new SimpleMailMessage();
        eMail.setTo(email);
        eMail.setSubject(subject);
        eMail.setText("Hello" +ru.getFirstName()+" "+ru.getLastName()+"!\n\n You have expressed interest in using our application. Please, clink on the link below and continue:\n\n"+confirmationUrl +"\n\n\nBest regards, \\nTheCinTeam");
        mailSender.send(eMail);
        
		return true;
	}
	
	
	public boolean registrationAdmins(String email, String pass, String type, HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String base = url.substring(0, url.length() - uri.length() + ctx.length());
		base=base+  "/";
		
        if(userRepository.findOne(email)!=null) {
        	return false;
        }
        
        User admin;
        
        switch (type){
        case "system":
        	admin=new SystemAdmin();
        	((SystemAdmin)admin).setDef(false);
        	((SystemAdmin)admin).setFirst_time(true);
        	((SystemAdmin)admin).setImage("http://theivykey.com/images/no-profile-image.png");
        	break;
        case "cinema":
        	admin=new CinemaAdmin();
        	((CinemaAdmin)admin).setFirst_time(true);
        	break;
        default:
        	admin=new FanZoneAdmin();
        	((FanZoneAdmin)admin).setFirst_time(true);
        	((FanZoneAdmin)admin).setImage("http://theivykey.com/images/no-profile-image.png");
        }
        
        admin.setEmail(email);
        admin.setPassword(pass);
        admin.setActivated("no");
        
        userRepository.save(admin);
        
        String token = UUID.randomUUID().toString();
        VerificationToken myToken = new VerificationToken();
        myToken.setToken(token);
        myToken.setUser(admin);
        tokenRepository.save(myToken);
        String subject = "Registration Confirmation";
        String confirmationUrl = base+"registrationConfirm.html?token="+token;
        
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
	    ((RegisteredUser)user).setNumOfReservations(0);
	    ((RegisteredUser)user).setUserMedal(RegisteredUser.Medal.None);
	    userRepository.save(user);
	    tokenRepository.delete(verificationToken.getId());
	    return true;
	}
}

package app.service;

import org.springframework.beans.factory.annotation.Autowired;

import app.model.RegisteredUser;
import app.repository.UserRepository;

public class RegistrationService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean registration(RegisteredUser ruser) {
		userRepository.save(ruser);
		return true;
	}
}

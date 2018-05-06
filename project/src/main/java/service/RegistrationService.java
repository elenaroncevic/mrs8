package service;

import org.springframework.beans.factory.annotation.Autowired;

import model.RegisteredUser;
import repository.UserRepository;

public class RegistrationService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean registration(RegisteredUser ruser) {
		userRepository.save(ruser);
		return true;
	}
}

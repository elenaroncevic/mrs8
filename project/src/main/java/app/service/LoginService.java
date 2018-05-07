package app.service;

import org.springframework.beans.factory.annotation.Autowired;

import app.model.User;
import app.repository.UserRepository;

public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findUser(String email) {
		User user = userRepository.findOne(email);
		return user;
	}
}

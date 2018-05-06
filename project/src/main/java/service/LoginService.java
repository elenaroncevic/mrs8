package service;

import org.springframework.beans.factory.annotation.Autowired;

import model.User;
import repository.UserRepository;

public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findUser(String email) {
		User user = userRepository.findOne(email);
		return user;
	}
}

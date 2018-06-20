package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.LoginUser;
import app.model.User;
import app.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findUser(String email, String pass) {
		User user = userRepository.findOne(email);
		if(user!=null && user.getPassword().equals(pass) && user.getActivated().equals("yes")) {
			return user;
		}else {
			return null;
		}
	}
}

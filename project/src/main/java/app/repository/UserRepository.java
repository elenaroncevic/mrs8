package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	
}

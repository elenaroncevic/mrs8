package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.RegisteredUser;
import app.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
	
    VerificationToken findByToken(String token);
    
    VerificationToken findByUser(RegisteredUser user);

}

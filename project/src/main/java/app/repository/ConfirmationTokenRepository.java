package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.ConfirmationToken;
import app.model.Ticket;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	
    ConfirmationToken findByToken(String token);
    
    ConfirmationToken findByTicket(Ticket ticket);
}

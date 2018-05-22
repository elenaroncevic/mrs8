package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.PromoUsed;
import app.model.RegisteredUser;



public interface PromoUsedRepository extends JpaRepository<PromoUsed,Long>{
	
	List<PromoUsed> findByOwner(RegisteredUser ru);
	List<PromoUsed> findByBuyer(RegisteredUser ru);
}

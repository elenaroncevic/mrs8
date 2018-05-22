package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.model.PromoOfficial;

public interface PromoOfficialRepository extends JpaRepository<PromoOfficial,Long>{//
	
	
    List<PromoOfficial> findByActivity(String activity);
    
}

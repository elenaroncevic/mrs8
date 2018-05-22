package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.model.PromoOfficial;

public interface PromoOfficialRepository extends JpaRepository<PromoOfficial,Long>{//
	
	/*
	@Query("SELECT p FROM promo_official p WHERE poactivity = :poactivity")
    List<PromoOfficial> findPromoOfficialByActivity(@Param("poactivity") String poactivity);
    */
}

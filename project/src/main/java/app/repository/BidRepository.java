package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.model.Bid;


public interface BidRepository extends JpaRepository<Bid,Long>{
	
	@Query("SELECT t from Bid t WHERE promo_puid = :promo_puid")
	List<Bid> getBidByPromoId(@Param("promo_puid") Long promo_puid);
}

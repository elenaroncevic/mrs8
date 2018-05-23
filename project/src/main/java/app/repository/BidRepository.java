package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.model.Bid;
import app.model.PromoUsed;
import app.model.RegisteredUser;


public interface BidRepository extends JpaRepository<Bid,Long>{
	
	List<Bid> findByBidder(RegisteredUser bidder);//
	List<Bid> findByPromo(PromoUsed promo);
	Bid findByBidderAndPromo(RegisteredUser bidder, PromoUsed promo);
}

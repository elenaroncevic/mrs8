package app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import app.dto.BidDTO;
import app.dto.PromoUsedDTO;
import app.model.Bid;
import app.model.PromoUsed;
import app.model.RegisteredUser;
import app.repository.BidRepository;
import app.repository.PromoUsedRepository;
import app.repository.UserRepository;

@Transactional
@Service
public class PromoUsedService {
	@Autowired 
	private PromoUsedRepository promoUsedRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	public List<PromoUsedDTO> listPromosUsedUnapproved() { //koji treba tek da se potvrde
		List<PromoUsed> listOfPromosUsed = promoUsedRepository.findAll();
		List<PromoUsedDTO> listOfPromosUsedDTO = new ArrayList<PromoUsedDTO>();
		for (PromoUsed pu : listOfPromosUsed){
			PromoUsedDTO pu_dto=new PromoUsedDTO();
			
			if (!pu.getActivity().equals("unapproved")){
				continue;
			}
			pu_dto.setActivity(pu.getActivity());
			
			if (pu.getBuyer()!=null){
				pu_dto.setBuyer_email(pu.getBuyer().getEmail());
			}else{
				pu_dto.setBuyer_email("none");
			}
			
			
			pu_dto.setDescription(pu.getDescription());
			pu_dto.setEnding_date(pu.getEndingDate());
			pu_dto.setId(pu.getId());
			pu_dto.setName(pu.getName());
			pu_dto.setOwner_email(pu.getOwner().getEmail());
			
			if (pu.getPrice()==null){
				pu_dto.setPrice(0);
			}
			
			pu_dto.setImage(pu.getImage());
			
			listOfPromosUsedDTO.add(pu_dto);
		}
		return listOfPromosUsedDTO;
		
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean approvePromoUsed(Long id) {
		PromoUsed pu = promoUsedRepository.findOne(id);
		pu.setActivity("approved");
		promoUsedRepository.save(pu);
		return true;
		
	}
	
	
	public boolean deletePromoUsed(Long id) { 
		PromoUsed pu = promoUsedRepository.findOne(id);
		pu.setActivity("deleted");
		promoUsedRepository.save(pu);
		return true;
		
	}


	public boolean createPromoUsed(String owner, String name, String description, String image, String date) {
		PromoUsed pu = new PromoUsed();
		pu.setActivity("unapproved");
		pu.setDescription(description);
		pu.setEndingDate(date);
		
		image = image.replace('+','/');
		image = image.replace('*','?');
		
		pu.setImage(image);
		pu.setName(name);
		
		//nadji ownera
		RegisteredUser ru = (RegisteredUser) userRepository.findOne(owner);
		pu.setOwner(ru);
		
		promoUsedRepository.save(pu);
		return true;
	}


	public List<PromoUsedDTO> listOthersPromosUsedApproved(String email) {
		List<PromoUsed> listOfPromosUsed = promoUsedRepository.findAll();
		List<PromoUsedDTO> listOfPromosUsedDTO = new ArrayList<PromoUsedDTO>();
		for (PromoUsed pu : listOfPromosUsed){			
			if (!pu.getActivity().equals("approved")){
				continue;
			}
			if (pu.getOwner().getEmail().equals(email)){
				continue;
			}
			PromoUsedDTO pu_dto=new PromoUsedDTO();
			pu_dto.setActivity(pu.getActivity());			
			
			pu_dto.setBuyer_email("none");
			
			pu_dto.setDescription(pu.getDescription());
			pu_dto.setEnding_date(pu.getEndingDate().replace('T', ' '));
			pu_dto.setId(pu.getId());
			pu_dto.setName(pu.getName());
			pu_dto.setOwner_email(pu.getOwner().getEmail());
			
			if (pu.getPrice()==null){
				pu_dto.setPrice(0);
			}
			
			pu_dto.setImage(pu.getImage());
			
			listOfPromosUsedDTO.add(pu_dto);
		}
		
		return listOfPromosUsedDTO;
	}


	public List<PromoUsedDTO> listMyBidsPromosUsed(String email) {
		List<PromoUsedDTO> listOfPromosUsedDTO = new ArrayList<PromoUsedDTO>();		
		RegisteredUser bidder = (RegisteredUser) userRepository.findOne(email);		
		List<Bid> listOfBids = bidRepository.findByBidder(bidder);
		List<PromoUsed> myBiddedPromos = new ArrayList<PromoUsed>();
		
		for (Bid b : listOfBids){
			myBiddedPromos.add(b.getPromo());
		}
			
		for (PromoUsed pu : myBiddedPromos){			
			PromoUsedDTO pu_dto=new PromoUsedDTO();
			pu_dto.setActivity(pu.getActivity());			
			
			if (pu.getBuyer()==null){
				pu_dto.setBuyer_email("none");
			}else{
				pu_dto.setBuyer_email(pu.getBuyer().getEmail());
			}
			
			pu_dto.setDescription(pu.getDescription());
			pu_dto.setEnding_date(pu.getEndingDate().replace('T',' '));
			pu_dto.setId(pu.getId());
			pu_dto.setName(pu.getName());
			pu_dto.setOwner_email(pu.getOwner().getEmail());
			
			if (pu.getPrice()==null){
				pu_dto.setPrice(0);
			}else{
				pu_dto.setPrice(pu.getPrice());
			}
			
			pu_dto.setImage(pu.getImage());
			
			listOfPromosUsedDTO.add(pu_dto);
		}
		
		return listOfPromosUsedDTO;
	}


	public List<PromoUsedDTO> listMyPostedPromosUsed(String email) {
		List<PromoUsedDTO> listOfPromosUsedDTO = new ArrayList<PromoUsedDTO>();		
		RegisteredUser owner = (RegisteredUser) userRepository.findOne(email);		
		
		List<PromoUsed> myPostedPromos = promoUsedRepository.findByOwner(owner);
			
		for (PromoUsed pu : myPostedPromos){
			if (pu.getActivity().equals("unapproved")){
				continue;
			}
			PromoUsedDTO pu_dto=new PromoUsedDTO();
			pu_dto.setActivity(pu.getActivity());			
			
			if (pu.getBuyer()==null){
				pu_dto.setBuyer_email("none");
			}else{
				pu_dto.setBuyer_email(pu.getBuyer().getEmail());
			}
			
			pu_dto.setDescription(pu.getDescription());
			pu_dto.setEnding_date(pu.getEndingDate().replace('T',' '));
			pu_dto.setId(pu.getId());
			pu_dto.setName(pu.getName());
			pu_dto.setOwner_email(email);
			
			if (pu.getPrice()==null){
				pu_dto.setPrice(0);
			}else{
				pu_dto.setPrice(pu.getPrice());
			}
			
			pu_dto.setImage(pu.getImage());
			
			listOfPromosUsedDTO.add(pu_dto);
		}
		
		return listOfPromosUsedDTO;
	}


	public List<PromoUsedDTO> listMyWonPromosUsed(String email) {
		List<PromoUsedDTO> listOfPromosUsedDTO = new ArrayList<PromoUsedDTO>();		
		RegisteredUser buyer = (RegisteredUser) userRepository.findOne(email);		
		
		List<PromoUsed> myWonPromos = promoUsedRepository.findByBuyer(buyer);
			
		for (PromoUsed pu : myWonPromos){
			PromoUsedDTO pu_dto=new PromoUsedDTO();
			pu_dto.setActivity(pu.getActivity());			
			pu_dto.setBuyer_email(email);
			
			pu_dto.setDescription(pu.getDescription());
			pu_dto.setEnding_date(pu.getEndingDate().replace('T',' '));
			pu_dto.setId(pu.getId());
			pu_dto.setName(pu.getName());
			pu_dto.setOwner_email(email);			
			pu_dto.setPrice(pu.getPrice());
		
			pu_dto.setImage(pu.getImage());
			
			listOfPromosUsedDTO.add(pu_dto);
		}
		
		return listOfPromosUsedDTO;
	}
	
	
	public PromoUsedDTO getPromoUsed(Long id) {
		PromoUsed pu = promoUsedRepository.findOne(id);
		PromoUsedDTO pu_dto = new PromoUsedDTO();
		
		pu_dto.setActivity(pu.getActivity());
		if (pu.getBuyer()==null){
			pu_dto.setBuyer_email("none");
		}else{
			pu_dto.setBuyer_email(pu.getBuyer().getEmail());
		}
		pu_dto.setDescription(pu.getDescription());
		pu_dto.setEnding_date(pu.getEndingDate().replace('T', ' '));
		pu_dto.setId(pu.getId());
		pu_dto.setImage(pu.getImage());
		pu_dto.setName(pu.getName());
		pu_dto.setOwner_email(pu.getOwner().getEmail());
		if (pu.getPrice()==null){
			pu_dto.setPrice(0);
		}else{
			pu_dto.setPrice(pu.getPrice());
		}
		
		return pu_dto;
	}
	
	
	public List<BidDTO> getBids(Long id) {
		PromoUsed pu = promoUsedRepository.findOne(id);
		List<Bid> bids = bidRepository.findByPromo(pu);
		List<BidDTO> bidsDTO = new ArrayList<BidDTO>();
		
		for (Bid b : bids){
			BidDTO bid_dto = new BidDTO();
			bid_dto.setBidder_email(b.getBidder().getEmail());
			bid_dto.setId(b.getId());
			bid_dto.setPrice(b.getPrice());
			bid_dto.setPromo_id(id);
			bidsDTO.add(bid_dto);//
		}
		
		return bidsDTO;
	}


	public boolean updateBid(String email, Double offer, Long id) {
		RegisteredUser ru = (RegisteredUser) userRepository.findOne(email);
		PromoUsed pu = promoUsedRepository.findOne(id);
		Bid bid = bidRepository.findByBidderAndPromo(ru, pu);
		if (bid==null){
			bid=new Bid();
			bid.setBidder(ru);
			bid.setPromo(pu);
		}
		bid.setPrice(offer);
		
		bidRepository.save(bid);
		return true;
	}


	public boolean updatePromoUsed(Long id, String name, String image, String description) {
		PromoUsed pu = promoUsedRepository.findOne(id);
		pu.setName(name);
		pu.setDescription(description);
		
		image = image.replace('+','/');
		image = image.replace('*','?');
		pu.setImage(image);
		
		promoUsedRepository.save(pu);
		return true;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public boolean chooseWinnerPromoUsed(Long bid) {	
		Bid b = bidRepository.findOne(bid);
		PromoUsed pu = b.getPromo();
		pu.setActivity("bought");
		pu.setBuyer(b.getBidder());
		pu.setPrice(b.getPrice());
		promoUsedRepository.save(pu);
		
		return true;
		
	}


	public boolean mailWinnerAndLosers(Long bid) {
		Bid b = bidRepository.findOne(bid);
		PromoUsed pu = b.getPromo();
		
		String subject = "Licitation over!";
		SimpleMailMessage eMail = new SimpleMailMessage();
		eMail.setSubject(subject);
		
		//obavestavam pobednika da je pobedio i ostale da su izgubili
		List<Bid> bids = bidRepository.findByPromo(pu);
		for (Bid one_bid : bids){
			if (one_bid.getBidder().getEmail().equals(b.getBidder().getEmail())){
				eMail.setTo(one_bid.getBidder().getEmail());        
		        eMail.setText("You have won the bid on "+ one_bid.getPromo().getName());
		        mailSender.send(eMail);
		        
			}else{
				eMail.setTo(one_bid.getBidder().getEmail());        
		        eMail.setText("You have lost the bid on "+ one_bid.getPromo().getName());
		        mailSender.send(eMail);
		        
			}
		}
		
		return true;
		
	}
	
	
			
}

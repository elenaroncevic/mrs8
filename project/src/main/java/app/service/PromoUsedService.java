package app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.PromoUsedDTO;
import app.model.Bid;
import app.model.PromoUsed;
import app.repository.BidRepository;
import app.repository.PromoUsedRepository;

@Service
public class PromoUsedService {
	@Autowired 
	private PromoUsedRepository promoUsedRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
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
			pu_dto.setEnding_time(pu.getEndingTime());
			pu_dto.setId(pu.getId());
			pu_dto.setName(pu.getName());
			pu_dto.setOwner_email(pu.getOwner().getEmail());
			
			if (pu.getPrice()==null){
				pu_dto.setPrice(0);
			}
			
			pu_dto.setImage(pu.getImage());
			
			
			//unapproved nemaju bidove, ovo ide u approved
			/*
			List<Bid> listOfBids = bidRepository.getBidByPromoId(pu_dto.getId());
			for (Bid b : listOfBids){
				pu_dto.getBid_id().add(b.getId());
				pu_dto.getBid_email().add(b.getBidder().getEmail());
				pu_dto.getBid_price().add(b.getPrice());
			}
			*/
			listOfPromosUsedDTO.add(pu_dto);
		}
		return listOfPromosUsedDTO;
		
	}
	
	
	public boolean approvePromoUsed(Long id) {
		PromoUsed pu = promoUsedRepository.findOne(id);
		pu.setActivity("approved");
		promoUsedRepository.save(pu);
		return true;
		
	}
	
	public boolean deletePromoUsed(Long id) { 
		PromoUsed pu = promoUsedRepository.findOne(id);
		//da li se menja u disapproved ili se brise?? ukoliko se brise skroz onda mi ova metoda ne treba
		pu.setActivity("deleted");
		promoUsedRepository.save(pu);
		return true;
		
	}
	
}

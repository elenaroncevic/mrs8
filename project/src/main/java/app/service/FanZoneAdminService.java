package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import app.model.Cinema;
import app.model.PromoOfficial;
import app.repository.CinemaRepository;
import app.repository.PromoOfficialRepository;


@Service
public class FanZoneAdminService {

	@Autowired
	private PromoOfficialRepository promoOfficialRepository;
	private CinemaRepository cinemaRepository;
	
	//name, description, image, cinema
	public boolean addNewPromoOfficial(String poName, String poDescription, String poImage, Double poPrice, Long cId, WebRequest req){
		String appUrl = req.getContextPath();
		
		//bice jedan bioskop
		Cinema cinema = cinemaRepository.findOne(cId);
	
		PromoOfficial newPromo = new PromoOfficial();
		newPromo.setActivity("created");
		newPromo.setBuyer(null);
		newPromo.setDescription(poDescription);
		newPromo.setImage(poImage);
		newPromo.setName(poName);
		newPromo.setPrice(poPrice);
		newPromo.setCinema(cinema);
		promoOfficialRepository.save(newPromo);
		
		return true;
	}
	
	
	
	
}

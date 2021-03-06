package app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import app.dto.PromoOfficialDTO;
import app.model.Cinema;
import app.model.FanZoneAdmin;
import app.model.PromoOfficial;
import app.model.User;
import app.repository.CinemaRepository;
import app.repository.PromoOfficialRepository;
import app.repository.UserRepository;


@Service
public class FanZoneAdminService {

	@Autowired
	private PromoOfficialRepository promoOfficialRepository;//
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	//name, description, image, cinema
	public boolean addNewPromoOfficial(String poName, String poDescription, String poImage, Double poPrice, Long cId, WebRequest req){
		//String appUrl = req.getContextPath();
		poImage=poImage.replace('+', '/');
		poImage=poImage.replace('*', '?');
		//bice jedan bioskop
		Cinema cinema = cinemaRepository.findOne(cId);
		
		PromoOfficial newPromo = new PromoOfficial();
		newPromo.setActivity("unreserved");
		newPromo.setBuyer(null);
		newPromo.setDescription(poDescription);
		newPromo.setImage(poImage);
		newPromo.setName(poName);
		newPromo.setPrice(poPrice);
		newPromo.setCinema(cinema);
		promoOfficialRepository.save(newPromo);
		
		return true;
	}

	public List<PromoOfficialDTO> listPromosOfficial() {
		List<PromoOfficial> listOfPromosOfficial = promoOfficialRepository.findAll();
		List<PromoOfficialDTO> listOfPromosOfficialDTO = new ArrayList<PromoOfficialDTO>();
		for (PromoOfficial po : listOfPromosOfficial){
			if (po.getActivity().equals("deleted")){
				continue;
			}
			PromoOfficialDTO po_dto=new PromoOfficialDTO();
			po_dto.setActivity(po.getActivity());
			
			if (po.getBuyer()!=null){
				po_dto.setBuyer_email(po.getBuyer().getEmail());
			}else{
				po_dto.setBuyer_email("none");
			}
			
			po_dto.setCinema_id(po.getCinema().getId());
			po_dto.setCinema_location(po.getCinema().getLocation());
			po_dto.setCinema_name(po.getCinema().getName());
			po_dto.setDescription(po.getDescription());
			po_dto.setId(po.getId());
			po_dto.setImage(po.getImage());
			po_dto.setName(po.getName());
			po_dto.setPrice(po.getPrice());
			
			listOfPromosOfficialDTO.add(po_dto);
		}
		return listOfPromosOfficialDTO;
		
	}

	public PromoOfficialDTO getPromoOfficial(Long id) {
		PromoOfficial po = promoOfficialRepository.findOne(id);
		if (po.getActivity().equals("deleted")){
			return null;
		}
		PromoOfficialDTO po_dto=new PromoOfficialDTO();
		po_dto.setActivity(po.getActivity());
		
		if (po.getBuyer()!=null){
			po_dto.setBuyer_email(po.getBuyer().getEmail());
		}else{
			po_dto.setBuyer_email("none");
		}
		
		po_dto.setCinema_id(po.getCinema().getId());
		po_dto.setCinema_location(po.getCinema().getLocation());
		po_dto.setCinema_name(po.getCinema().getName());
		po_dto.setDescription(po.getDescription());
		po_dto.setId(po.getId());
		po_dto.setImage(po.getImage());
		po_dto.setName(po.getName());
		po_dto.setPrice(po.getPrice());
		
		return po_dto;
	}

	public boolean updatePromoOfficial(String poName, String poDescription, String poImage, Double poPrice, Long cId,
			Long poId) {
		poImage=poImage.replace('+', '/');
		poImage=poImage.replace('*', '?');

		Cinema cinema = cinemaRepository.findOne(cId);
		
		PromoOfficial po=promoOfficialRepository.findOne(poId);
		po.setName(poName);
		po.setCinema(cinema);
		po.setDescription(poDescription);
		po.setImage(poImage);
		po.setName(poName);
		po.setPrice(poPrice);
		
		promoOfficialRepository.save(po);
		
		return true;
	}

	public boolean deletePromoOfficial(Long id) {
		PromoOfficial po = promoOfficialRepository.findOne(id);
		po.setActivity("deleted");
		promoOfficialRepository.save(po);
		return true;
	}

	public User changeFanZoneAdminInfo(String email, String image, String pass) {
		image=image.replace('+', '/');
		image=image.replace('*', '?');
		FanZoneAdmin fanZoneAdmin = (FanZoneAdmin) userRepository.findOne(email);
		if (!pass.equals(fanZoneAdmin.getPassword())){
			fanZoneAdmin.setFirst_time(false);
		}
		fanZoneAdmin.setImage(image);
		fanZoneAdmin.setPassword(pass);
		FanZoneAdmin fza = userRepository.save(fanZoneAdmin);
		return fza;
	}
	
	
	
	
	
}

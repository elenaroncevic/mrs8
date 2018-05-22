package app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.PromoOfficialDTO;
import app.model.PromoOfficial;
import app.model.RegisteredUser;
import app.repository.PromoOfficialRepository;
import app.repository.UserRepository;

@Service
public class PromoOfficialService {
	@Autowired 
	private PromoOfficialRepository promoOfficialRepository;//
	
	@Autowired 
	private UserRepository userRepository;
	
	public List<PromoOfficialDTO> listUnreservedPromosOfficial() {
		//List<PromoOfficial> listOfPromosOfficial = promoOfficialRepository.findPromoOfficialByActivity("unreserved");
		
		List<PromoOfficial> listOfPromosOfficial = promoOfficialRepository.findAll();
		
		List<PromoOfficialDTO> listOfPromosOfficialDTO = new ArrayList<PromoOfficialDTO>();
		for (PromoOfficial po : listOfPromosOfficial){
			if (!po.getActivity().equals("unreserved")){
				continue;
			}
			PromoOfficialDTO po_dto=new PromoOfficialDTO();
			po_dto.setActivity(po.getActivity());
			
			//aktivnost je unreserved i ne postoji buyer
			po_dto.setBuyer_email("none");						
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
		return listOfPromosOfficialDTO;//
		
	}

	public boolean reservePromoOfficial(Long poId, String currentUser) {
		PromoOfficial po = promoOfficialRepository.findOne(poId);
		po.setActivity("reserved");
		
		RegisteredUser ru = (RegisteredUser) userRepository.findOne(currentUser);
		po.setBuyer(ru);
		
		promoOfficialRepository.save(po);
		return true;
	}

	public List<PromoOfficialDTO> listReservedPromosOfficial(String email) {
		List<PromoOfficial> listOfPromosOfficial = promoOfficialRepository.findAll();
		List<PromoOfficialDTO> listOfReservedPromosOfficialDTO = new ArrayList<PromoOfficialDTO>();
		
		for (PromoOfficial po : listOfPromosOfficial){
			if (po.getBuyer()==null){
				continue;
			}
			if (!po.getBuyer().getEmail().equals(email)){
				continue;
			}
			PromoOfficialDTO po_dto=new PromoOfficialDTO();
			po_dto.setActivity(po.getActivity());
			
			//aktivnost je reserved i postoji buyer
			po_dto.setBuyer_email(po.getBuyer().getEmail());						
			po_dto.setCinema_id(po.getCinema().getId());
			po_dto.setCinema_location(po.getCinema().getLocation());
			po_dto.setCinema_name(po.getCinema().getName());
			po_dto.setDescription(po.getDescription());
			po_dto.setId(po.getId());
			po_dto.setImage(po.getImage());
			po_dto.setName(po.getName());
			po_dto.setPrice(po.getPrice());
			
			listOfReservedPromosOfficialDTO.add(po_dto);
		}
		return listOfReservedPromosOfficialDTO;
	}

	public boolean unreservePromoOfficial(Long id) {
		PromoOfficial po = promoOfficialRepository.findOne(id);
		po.setBuyer(null);
		promoOfficialRepository.save(po);
		return true;
	}
}

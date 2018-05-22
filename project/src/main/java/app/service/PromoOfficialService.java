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
	private PromoOfficialRepository promoOfficialRepository;
	
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
			
			//bice null buyer jer je aktivnost unreserved, a ne reserved
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
		return listOfPromosOfficialDTO;
		
	}

	public boolean reservePromoOfficial(Long poId, String currentUser) {
		PromoOfficial po = promoOfficialRepository.findOne(poId);
		po.setActivity("reserved");
		
		RegisteredUser ru = (RegisteredUser) userRepository.findOne(currentUser);
		po.setBuyer(ru);
		
		promoOfficialRepository.save(po);
		return true;
	}
}

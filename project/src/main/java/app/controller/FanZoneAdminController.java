package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import app.dto.PromoOfficialDTO;
import app.model.PromoOfficial;
import app.service.FanZoneAdminService;

@RestController
public class FanZoneAdminController {
	@Autowired
	private FanZoneAdminService fanZoneAdminService;//
	
	@RequestMapping(value="/fan_zone_admin/add_promo_official/{poName}/{poDescription}/{poImage}/{poPrice}/{cId}", method=RequestMethod.GET)
	public ResponseEntity<Void> addNewPromoOfficial(@PathVariable("poName") String poName, @PathVariable("poDescription") String poDescription, @PathVariable("poImage") String poImage, @PathVariable("poPrice") Double poPrice, @PathVariable("cId") Long cId, WebRequest req){
		if (fanZoneAdminService.addNewPromoOfficial(poName, poDescription, poImage, poPrice, cId, req)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {      
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value="/fan_zone_admin/update_promo_official/{poName}/{poDescription}/{poImage}/{poPrice}/{cId}/{poId}", method=RequestMethod.GET)
	public ResponseEntity<Void> updatePromoOfficial(@PathVariable("poName") String poName, @PathVariable("poDescription") String poDescription, @PathVariable("poImage") String poImage, @PathVariable("poPrice") Double poPrice, @PathVariable("cId") Long cId, @PathVariable("poId") Long poId){
		if (fanZoneAdminService.updatePromoOfficial(poName, poDescription, poImage, poPrice, cId, poId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {      
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	
	@RequestMapping(value = "/fan_zone_admin/delete_promo_official/{id}", method = RequestMethod.GET)
	public ResponseEntity<Void> deletePromoOfficial(@PathVariable("id") Long id) {
		if (fanZoneAdminService.deletePromoOfficial(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {      
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/fan_zone_admin/list_promos_official", method = RequestMethod.GET)
	public ResponseEntity<List<PromoOfficialDTO>> listPromosOfficial() {
		List<PromoOfficialDTO> listOfPromosOfficialDTO = fanZoneAdminService.listPromosOfficial();
		return new ResponseEntity<>(listOfPromosOfficialDTO,HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/fan_zone_admin/get_promo_official/{id}", method = RequestMethod.GET)
	public ResponseEntity<PromoOfficialDTO> getPromoOfficial(@PathVariable("id") Long id) {
		PromoOfficialDTO promoOfficialDTO = fanZoneAdminService.getPromoOfficial(id);
		return new ResponseEntity<>(promoOfficialDTO,HttpStatus.OK);	
	}	
	
}

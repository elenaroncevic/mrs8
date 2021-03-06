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
import app.dto.PromoUsedDTO;
import app.model.PromoOfficial;
import app.model.User;
import app.service.FanZoneAdminService;
import app.service.PromoUsedService;

@RestController
public class FanZoneAdminController {
	@Autowired
	private FanZoneAdminService fanZoneAdminService;//
	
	@Autowired
	private PromoUsedService promoUsedService;//
	
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
	
	
	@RequestMapping(value = "/fan_zone_admin/list_promos_unapproved", method = RequestMethod.GET)
	public ResponseEntity<List<PromoUsedDTO>> listPromosUnapproved() {
		List<PromoUsedDTO> listOfPromosUnapprovedDTO = promoUsedService.listPromosUsedUnapproved();
		return new ResponseEntity<>(listOfPromosUnapprovedDTO,HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/fan_zone_admin/approve_promo_used/{id}", method = RequestMethod.GET)
	public ResponseEntity<Void> approvePromoUsed(@PathVariable("id") Long id) {
		if (promoUsedService.approvePromoUsed(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {      
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/fan_zone_admin/delete_promo_used/{id}", method = RequestMethod.GET)
	public ResponseEntity<Void> deletePromoUsed(@PathVariable("id") Long id) {
		if (promoUsedService.deletePromoUsed(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {      
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/fan_zone_admin/change_user_info/{email}/{image}/{pass}", method = RequestMethod.POST)
	public ResponseEntity<User> changeFanZoneAdminInfo(@PathVariable("email") String email, @PathVariable("image") String image, @PathVariable("pass") String pass) {
		User changedUser = fanZoneAdminService.changeFanZoneAdminInfo(email, image, pass);
		return new ResponseEntity<>(changedUser,HttpStatus.OK);	
	}
}

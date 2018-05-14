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
	
	@RequestMapping(value = "/fan_zone_admin/list_promos_official", method = RequestMethod.GET)
	public ResponseEntity<List<PromoOfficial>> listPromosOfficial() {
		List<PromoOfficial> listOfPromosOfficial = fanZoneAdminService.listPromosOfficial();
		return new ResponseEntity<>(listOfPromosOfficial,HttpStatus.OK);	
	}
	
}

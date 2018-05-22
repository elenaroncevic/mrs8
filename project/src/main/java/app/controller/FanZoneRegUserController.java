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
import app.service.PromoOfficialService;
import app.service.PromoUsedService;

@RestController
public class FanZoneRegUserController {
	
	@Autowired
	PromoUsedService promoUsedService;
	
	@Autowired
	PromoOfficialService promoOfficialService;
	
	@RequestMapping(value="/reg_user/add_pu/{owner}/{puName}/{puDescription}/{puImage}/{puDate}/{puTime}", method=RequestMethod.GET)
	public ResponseEntity<Void> createPromoUsed(@PathVariable("owner") String owner, @PathVariable("puName") String name, @PathVariable("puDescription") String description, @PathVariable("puImage") String image, @PathVariable("puDate") String date, @PathVariable("puTime") String time){
		if (promoUsedService.createPromoUsed(owner, name, description, image, date, time)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);//
		}

	}
	
	@RequestMapping(value = "/reg_user/list_unreserved_po", method = RequestMethod.GET)
	public ResponseEntity<List<PromoOfficialDTO>> listPromosOfficialUnreserved() {
		List<PromoOfficialDTO> listOfPromosUnreservedDTO = promoOfficialService.listUnreservedPromosOfficial();
		return new ResponseEntity<>(listOfPromosUnreservedDTO,HttpStatus.OK);	
	}
	
	
	@RequestMapping(value="/reg_user/reserve_po/{user}/{poId}", method=RequestMethod.GET)
	public ResponseEntity<Void> reservePromoOfficial(@PathVariable("poId") Long poId, @PathVariable("user") String currentUser){
		if (promoOfficialService.reservePromoOfficial(poId, currentUser)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);//
		}

	}
	
	@RequestMapping(value = "/reg_user/list_reserved_po/{email}/{nesto}", method = RequestMethod.GET)
	public ResponseEntity<List<PromoOfficialDTO>> listPromosOfficialReserved(@PathVariable("email") String email, @PathVariable("nesto") String nesto) {		
		List<PromoOfficialDTO> listOfPromosReservedDTO = promoOfficialService.listReservedPromosOfficial(email);
		return new ResponseEntity<>(listOfPromosReservedDTO,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/reg_user/unreserve_po/{id}", method=RequestMethod.GET)
	public ResponseEntity<Void> unreservePromoOfficial(@PathVariable("id") Long id){
		if (promoOfficialService.unreservePromoOfficial(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);//
		}

	}
}

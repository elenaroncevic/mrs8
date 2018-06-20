package app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import app.dto.PromoUsedDTO;
import app.model.CinemaAdmin;
import app.model.PointScale;
import app.model.User;
import app.service.RegistrationService;
import app.service.SystemAdminService;

@RestController
public class SystemAdminController {
	
	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	SystemAdminService systemAdminService;
	
	
	@RequestMapping(value="/system_admin/register_new_admin/{admEmail}/{admPass1}/{admType}", method=RequestMethod.POST)
	public ResponseEntity<Void> checkData(@PathVariable("admEmail") String email, @PathVariable("admPass1") String pass1, @PathVariable("admType") String type,  HttpServletRequest req){
		if (registrationService.registrationAdmins(email, pass1, type, req)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);//
		}

	}
	
	@RequestMapping(value = "/system_admin/list_cinema_admins", method = RequestMethod.GET)
	public ResponseEntity<List<CinemaAdmin>> listCinemaAdmins() {
		List<CinemaAdmin> listOfCinemaAdmins = systemAdminService.listCinemaAdmins();
		return new ResponseEntity<List<CinemaAdmin>>(listOfCinemaAdmins,HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/system_admin/change_user_info/{email}/{image}/{pass}", method = RequestMethod.POST)
	public ResponseEntity<User> changeSystemAdminInfo(@PathVariable("email") String email, @PathVariable("image") String image, @PathVariable("pass") String pass) {
		User changedUser = systemAdminService.changeSystemAdminInfo(email, image, pass);
		return new ResponseEntity<>(changedUser,HttpStatus.OK);	
	}
	
	@RequestMapping(value="/system_admin/get_scale", method=RequestMethod.GET)
	public ResponseEntity<PointScale> getPointScale(){
		PointScale pointScale=systemAdminService.getPointScale();
		return new ResponseEntity<>(pointScale, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/system_admin/update_scale/{copper}/{silver}/{golden}/{copper_discount}/{silver_discount}/{golden_discount}", method = RequestMethod.GET)
	public ResponseEntity<PointScale> updatePointScale(@PathVariable("copper") Integer copper, @PathVariable("silver") Integer silver, @PathVariable("golden") Integer golden, @PathVariable("copper_discount") Integer copper_discount, @PathVariable("silver_discount") Integer silver_discount, @PathVariable("golden_discount") Integer golden_discount) {
		PointScale pointScale = systemAdminService.updatePointScale(copper, silver, golden, copper_discount, silver_discount, golden_discount);
		return new ResponseEntity<>(pointScale,HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/system_admin/register_cinema/{adminEmail}/{name}/{description}/{lat}/{lng}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Void> registerBuilding(@PathVariable("adminEmail") String adminEmail, @PathVariable("name") String buildingName, @PathVariable("type") String buildingType, @PathVariable("lat") Double latitude, @PathVariable("lng") Double longitude,  @PathVariable("description") String buildingDescription) {
		if (systemAdminService.registerBuilding(adminEmail, buildingName, buildingType, latitude, longitude, buildingDescription)){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

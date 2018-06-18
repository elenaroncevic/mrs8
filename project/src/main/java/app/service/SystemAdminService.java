package app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Cinema;
import app.model.Cinema.BuildingType;
import app.model.CinemaAdmin;
import app.model.FanZoneAdmin;
import app.model.PointScale;
import app.model.SystemAdmin;
import app.model.User;
import app.repository.CinemaRepository;
import app.repository.PointScaleRepository;
import app.repository.UserRepository;


@Service
public class SystemAdminService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	@Autowired
	private PointScaleRepository pointScaleRepository;
	
	public List<CinemaAdmin> listCinemaAdmins() {
		List<User> users=userRepository.findAll();
		List<CinemaAdmin> listOfCinemaAdmins=new ArrayList<CinemaAdmin>();
		
		for (User u : users){
			if (u instanceof CinemaAdmin){
				if (((CinemaAdmin)u).getActivated().equals("yes")){
					listOfCinemaAdmins.add((CinemaAdmin)u);
				}
				
			}
			
		}
		
		return listOfCinemaAdmins;
	}

	public User changeSystemAdminInfo(String email, String image, String pass) {
		image=image.replace('+', '/');
		image=image.replace('*', '?');
		SystemAdmin systemAdmin = (SystemAdmin) userRepository.findOne(email);
		if (!pass.equals(systemAdmin.getPassword())){
			systemAdmin.setFirst_time(false);
		}
		systemAdmin.setImage(image);
		systemAdmin.setPassword(pass);
		SystemAdmin sa = userRepository.save(systemAdmin);
		return sa;
	}

	public PointScale getPointScale() {
		String id="1";
		PointScale ps = pointScaleRepository.findOne(Long.parseLong(id));
		return ps;
	}

	public PointScale updatePointScale(Integer copper, Integer silver, Integer golden) {
		String id="1";
		PointScale ps = pointScaleRepository.findOne(Long.parseLong(id));
		ps.setCopper(copper);
		ps.setSilver(silver);
		ps.setGolden(golden);
		ps = pointScaleRepository.save(ps);
		return ps;
	}

	public boolean registerBuilding(String adminEmail, String buildingName, String buildingType, Double latitude, Double longitude, String buildingDescription) {
		List<Cinema> cinemas=cinemaRepository.findByName(buildingName);
		if (!cinemas.isEmpty()){
			return false;
		}else{
			Cinema cinema=new Cinema();
			CinemaAdmin cinemaAdmin = (CinemaAdmin)userRepository.findOne(adminEmail);
			cinema.setAdmin(cinemaAdmin);
			cinema.setName(buildingName);
			cinema.setDescription(buildingDescription);
			cinema.setLatitude(latitude);
			cinema.setLongitude(longitude);
			if (buildingType.equals("cinema")){
				cinema.setType(BuildingType.Cinema);
			}else{
				cinema.setType(BuildingType.Theater);
			}
			
			cinemaRepository.save(cinema);
			return true;
		}
		
		
		
	}
	
	
}

package app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Cinema;
import app.repository.CinemaRepository;

@Service
public class ListCinemasService {
	@Autowired
	private CinemaRepository cinemaRepository;
	
	public List<Cinema> getAllCinemasTheaters() {
		return cinemaRepository.findAll();
	}
	
	public List<Cinema> getCinemas() {
		List<Cinema> cinemaList = getAllCinemasTheaters();
		List<Cinema> retValue = new ArrayList<Cinema>();
		for(Cinema cin : cinemaList) {
			if(cin.getType()==Cinema.BuildingType.Cinema) {
				retValue.add(cin);
			}
		}
		return retValue;
	}
	
	public List<Cinema> getTheaters(){
		List<Cinema> cinemaList = getAllCinemasTheaters();
		List<Cinema> retValue = new ArrayList<Cinema>();
		for(Cinema cin : cinemaList) {
			if(cin.getType()==Cinema.BuildingType.Theater) {
				retValue.add(cin);
			}
		}
		return retValue;
	}
}

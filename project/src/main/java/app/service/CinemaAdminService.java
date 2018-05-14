package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Cinema;
import app.repository.CinemaRepository;
import app.repository.UserRepository;

@Service
public class CinemaAdminService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	public Cinema editCinemaBasic(Long id, String name, String location, String description){
		Cinema cinema = cinemaRepository.findOne(id);
		cinema.setName(name);
		cinema.setLocation(location);
		cinema.setDescription(description);
		cinemaRepository.save(cinema);
		return cinema;
	}
}

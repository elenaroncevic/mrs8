package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Auditorium;
import app.model.Cinema;
import app.model.Movie;
import app.model.Projection;
import app.repository.CinemaRepository;
import app.repository.ProjectionRepository;
import app.repository.UserRepository;

@Service
public class CinemaAdminService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CinemaRepository cinemaRepository;
	
	@Autowired
	private ProjectionRepository projectionRepository;
	
	public Cinema editCinemaBasic(Long id, String name, String location, String description){
		Cinema cinema = cinemaRepository.findOne(id);
		cinema.setName(name);
		cinema.setLocation(location);
		cinema.setDescription(description);
		cinemaRepository.save(cinema);
		return cinema;
	}
	/*
	public List<Movie> getCinemaMovies(Long id){
		Set<Auditorium> projs = cinemaRepository.findOne(id).getAuditoriums();
		List<Movie> retValue = new ArrayList<Movie>();
		for(Auditorium aud : projs) {
			for(Projection proj : aud.getProjections()) {
				if(!retValue.contains(proj.getMovie())) {
					retValue.add(proj.getMovie());
				}
			}
		}
		return retValue;
	}
	
	public String getAudFromProjection(Long id){		
		return "Room:"+projectionRepository.findOne(id).getAuditorium().getId();
	}
*/
	public Movie getProjectionMovie(Long id) {
		System.out.println("getMovie Service");
		return projectionRepository.findOne(id).getMovie();
		 
	}
}

package app.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Auditorium;
import app.model.Cinema;
import app.model.Movie;
import app.model.Projection;
import app.model.User;
import app.repository.CinemaRepository;
import app.repository.MovieRepository;
import app.repository.ProjectionRepository;
import app.repository.UserRepository;

@Service
public class CinemaAdminService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
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
		return projectionRepository.findOne(id).getMovie();
		 
	}
	
	
	
	public User getUser(String id) {
		return userRepository.findOne(id);
		 
	}
	
	public Cinema getCinema(Long id) {
		return cinemaRepository.findOne(id);
		 
	}
	public void removeProjection(Long id) {
		projectionRepository.delete(id);		 
	}
	public List<Movie> getMovies() {
		return movieRepository.findAll();
		
	}
	public Projection addProjection(Long id, Long aid, Calendar calendar, Double price, Long movieId) {
		Calendar now = Calendar.getInstance();
		Date date = new Date(calendar.getTimeInMillis());
		Projection p = new Projection( date, price);
		Movie movie = movieRepository.findOne(movieId);
		p.setMovie(movie);
		Cinema cinema= cinemaRepository.findOne(id);
		for(Auditorium auditorium : cinema.getAuditoriums()){
			if (auditorium.getId()==aid){
				/*for(Projection proj : auditorium.getProjections()){
					Calendar pc = Calendar.getInstance();
					pc.setTime(proj.getDate());
					pc.mi
					if (proj.getDate().before(now.s)){
						
					}
				}*/
				p.setAuditorium(auditorium);
			}
		}
		
		p.setTime("2018-05-18 15:15:00");
		projectionRepository.save(p);
		return p;
	}
	
}

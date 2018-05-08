package app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Cinema;
import app.repository.CinemaRepository;

@Service
public class ListCinemasService {
	@Autowired
	private CinemaRepository cinemaRepository;
	
	public List<Cinema> list() {
		List<Cinema> cinemaList = cinemaRepository.findAll();
		return cinemaList;
	}
}

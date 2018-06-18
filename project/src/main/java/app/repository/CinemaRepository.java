package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema,Long> {
	List<Cinema> findByName(String name);
}

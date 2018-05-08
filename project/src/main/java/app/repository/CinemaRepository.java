package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Cinema;

public interface CinemaRepository extends JpaRepository<Cinema,Long> {

}

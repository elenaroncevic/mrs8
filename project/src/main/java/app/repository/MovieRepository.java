package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}

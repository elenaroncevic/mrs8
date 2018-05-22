package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Auditorium;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Long>{

}

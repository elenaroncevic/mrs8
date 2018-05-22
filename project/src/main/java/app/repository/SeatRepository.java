package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

}

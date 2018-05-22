package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByNumber(Integer number);

	List<Seat> findByNumberBetween(Integer number1, Integer number2);



}

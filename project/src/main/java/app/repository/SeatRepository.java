package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByNumber(String number);

	List<Seat> findByNumberBetween(String number1, String number2);



}

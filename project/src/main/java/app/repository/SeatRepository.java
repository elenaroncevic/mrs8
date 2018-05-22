package app.repository;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByNumber(String number);

	List<Seat> findByNumberBetween(String number1, String number2);
=======
import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {

>>>>>>> branch 'master' of https://github.com/elenaroncevic/mrs8
}

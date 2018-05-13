package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

}

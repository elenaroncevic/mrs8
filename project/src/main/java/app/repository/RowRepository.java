package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Row;

public interface RowRepository extends JpaRepository<Row,Long> {

}

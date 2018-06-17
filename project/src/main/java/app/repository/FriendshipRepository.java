package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long>{

}

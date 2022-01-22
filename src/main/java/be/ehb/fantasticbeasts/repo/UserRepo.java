package be.ehb.fantasticbeasts.repo;

import be.ehb.fantasticbeasts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}

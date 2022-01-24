package be.ehb.fantasticbeasts.repo;

import be.ehb.fantasticbeasts.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}

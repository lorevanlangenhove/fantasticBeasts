package be.ehb.fantasticbeasts.repo;

import be.ehb.fantasticbeasts.entities.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepo extends CrudRepository<Cart, Integer> {
    Optional<Cart> findByUserEmail(String email);
}


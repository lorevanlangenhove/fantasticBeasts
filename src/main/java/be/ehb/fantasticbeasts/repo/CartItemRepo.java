package be.ehb.fantasticbeasts.repo;

import be.ehb.fantasticbeasts.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepo extends CrudRepository<CartItem, Integer> {
}


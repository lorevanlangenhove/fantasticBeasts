package be.ehb.fantasticbeasts.repo;

import be.ehb.fantasticbeasts.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, Integer> {
}

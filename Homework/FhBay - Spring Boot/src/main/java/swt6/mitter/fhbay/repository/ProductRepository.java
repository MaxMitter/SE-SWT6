package swt6.mitter.fhbay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.mitter.fhbay.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCase(String name);

    List<Product> findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);

    List<Product> findByDescriptionContainsIgnoreCase(String description);


}

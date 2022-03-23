package swt6.mitter.fhbay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.mitter.fhbay.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

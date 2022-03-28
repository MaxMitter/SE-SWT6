package swt6.mitter.fhbay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swt6.mitter.fhbay.domain.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCase(String name);

    List<Product> findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);

    List<Product> findByDescriptionContainsIgnoreCase(String description);

    @Query("select p from Product p where p.Status = 2")
    List<Product> findByBiddingClosed();

    @Query("select p from Product p where p.Status = 1 and p.AuctionEnd < current_timestamp")
    List<Product> findAuctionsStatusOpenButExpired();
}

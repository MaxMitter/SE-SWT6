package swt6.mitter.fhbay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import swt6.mitter.fhbay.domain.Bid;
import swt6.mitter.fhbay.domain.Product;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByArticleEquals(@NonNull Product Article);
}

package swt6.mitter.fhbay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.mitter.fhbay.domain.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
}

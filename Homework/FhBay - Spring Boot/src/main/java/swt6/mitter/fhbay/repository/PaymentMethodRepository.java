package swt6.mitter.fhbay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.mitter.fhbay.domain.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}

package swt6.mitter.fhbay.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import swt6.mitter.fhbay.domain.Bid;
import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.domain.Product;
import swt6.mitter.fhbay.domain.ProductStatus;
import swt6.mitter.fhbay.logic.service.FhBayService;

import java.time.LocalDateTime;

@Order(-1)
@Component
@Profile("dev")
public class DbInitializer implements CommandLineRunner {

    @Autowired
    private FhBayService service;

    private Logger logger = LoggerFactory.getLogger(DbInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        Customer testCustomer = new Customer("Test", "test@mail.com", null, null);
        Product testProduct = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        var testBid = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);

        testCustomer = service.syncCustomer(testCustomer);
        testProduct = service.syncProduct(testProduct);
        testBid = service.syncBid(testBid);
    }
}

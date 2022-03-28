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
        Customer testCustomer1 = new Customer("Test", "test@mail.com", null, null);
        Customer testCustomer2 = new Customer("Rainer Zufall", "rainer@mail.com", null, null);
        Customer testCustomer3 = new Customer("Max Mustermann", "max@mail.com", null, null);
        Customer testCustomer4 = new Customer("Marie Market", "marie@mail.com", null, null);
        Product testProduct1 = new Product("Testproduct", "this is used for testing", 100,
                0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer1, null, ProductStatus.OPEN_FOR_BIDS);
        Product testProduct2 = new Product("Gutenberg Bible", "a very old book", 2000,
                0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer2, null, ProductStatus.OPEN_FOR_BIDS);
        var testBid1 = new Bid(101, LocalDateTime.now(), testCustomer1, testProduct1);
        var testBid2 = new Bid(110, LocalDateTime.now(), testCustomer2, testProduct1);
        var testBid3 = new Bid(200, LocalDateTime.now(), testCustomer3, testProduct1);
        var testBid4 = new Bid(1000, LocalDateTime.now(), testCustomer4, testProduct1);

        testCustomer1 = service.syncCustomer(testCustomer1);
        testCustomer2 = service.syncCustomer(testCustomer2);
        testCustomer3 = service.syncCustomer(testCustomer3);
        testCustomer4 = service.syncCustomer(testCustomer4);

        testProduct1 = service.syncProduct(testProduct1);
        testProduct2 = service.syncProduct(testProduct2);
        testBid1 = service.syncBid(testBid1);
        testBid2 = service.syncBid(testBid2);
        testBid3 = service.syncBid(testBid3);
        testBid4 = service.syncBid(testBid4);
    }
}

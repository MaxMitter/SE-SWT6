package swt6.client;

import swt6.orm.domain.Address;
import swt6.orm.domain.Bankaccount;
import swt6.orm.domain.Customer;
import swt6.orm.domain.PaymentMethod;
import swt6.orm.repository.CustomerRepository;
import swt6.orm.repository.PaymentMethodRepository;
import swt6.util.JpaUtil;

public class TestClient {

    public static void main(String[] args) {
        JpaUtil.getEntityManagerFactory();
        var paymentRepo = new PaymentMethodRepository();
        var customerRepo = new CustomerRepository();

        var addr1 = new Address("4040", "Linz", "Galvanistraße 16");
        var addr2 = new Address("5020", "Salzburg", "Bahnhofstraße 29");
        var cust1 = new Customer("Max Mustermann", "max@muster.com", addr1, addr1);
        var method = new Bankaccount(cust1, "AT47700000000000", "ATSBGRAI09");

        cust1 = paymentRepo.addPaymentMethod(cust1, method);
        paymentRepo.removePaymentMethod(cust1, method);

        customerRepo.createCustomer(cust1);
        customerRepo.updateShippingAddress(cust1, addr2);

        JpaUtil.closeEntityManagerFactory();
    }
}

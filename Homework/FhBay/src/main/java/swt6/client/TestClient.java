package swt6.client;

import net.bytebuddy.asm.Advice;
import swt6.orm.dao.DaoFactory;
import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import java.time.LocalDateTime;

public class TestClient {

    public static void main(String[] args) {
        JpaUtil.getEntityManagerFactory();
        try {
//            TestCustomer();
//            TestBids();
            TestProducts();

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        JpaUtil.closeEntityManagerFactory();
    }

    private static void TestProducts() {
        var customerDao = DaoFactory.getCustomerDao();
        var productDao = DaoFactory.getProductDao();
        var addr1 = new Address("4040", "Linz", "Galvanistraße 16");
        var cust1 = new Customer("Max Mustermann", "max@muster.com", addr1, addr1);
        var method = new Bankaccount(cust1, "AT47700000000000", "ATSBGRAI09");
        var product = new Product("Siemens Lufthaken", "Very useful", 100.0, 100.0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), cust1, cust1, ProductStatus.SAVED);

        System.out.println("######### add customer #########");
        customerDao.insert(cust1);
        JpaUtil.commit();

        System.out.println("######### add product #########");
        productDao.insert(product);
        JpaUtil.commit();

        System.out.println("######### find product by name #########");
        System.out.println(productDao.getByName("siemens"));

        System.out.println("######### find product by name or description #########");
        System.out.println(productDao.getByNameOrDescription("siemens"));
        System.out.println(productDao.getByNameOrDescription("useful"));

        System.out.println("######### find product by status #########");
        System.out.println(productDao.getByStatus(ProductStatus.SAVED));
        System.out.println(productDao.getByStatus(ProductStatus.NOT_SELLABLE));

        System.out.println("######### find product by seller #########");
        System.out.println(productDao.getBySeller(cust1));

        System.out.println("######### find product by buyer #########");
        System.out.println(productDao.getByBuyer(cust1));

    }

    private static void TestBids() {
        var customerDao = DaoFactory.getCustomerDao();
        var productDao = DaoFactory.getProductDao();
        var bidDao = DaoFactory.getBidDao();

        var addr1 = new Address("4040", "Linz", "Galvanistraße 16");
        var cust1 = new Customer("Max Mustermann", "max@muster.com", addr1, addr1);
        var method = new Bankaccount(cust1, "AT47700000000000", "ATSBGRAI09");
        var product = new Product("Siemens Lufthaken", "Very usefull", 100.0, 100.0,
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), cust1, null, ProductStatus.SAVED);
        var bid = new Bid(150.0, LocalDateTime.now(), cust1, product);
        var bid2 = new Bid(200.0, LocalDateTime.now(), cust1, product);

        System.out.println("######### add customer #########");
        customerDao.insert(cust1);
        JpaUtil.commit();

        System.out.println("######### add product #########");
        productDao.insert(product);
        JpaUtil.commit();

        System.out.println("######### add bid #########");
        bidDao.insert(bid);
        bidDao.insert(bid2);
        JpaUtil.commit();

        System.out.println("######### print all bids #########");
        bidDao.getAll().forEach(customer -> System.out.println(customer));

        System.out.println("######### print highest bid #########");
        System.out.println(bidDao.getHighestBid(product.getId()));

    }

    private static void TestCustomer() {
        var customerDao = DaoFactory.getCustomerDao();

        var addr1 = new Address("4040", "Linz", "Galvanistraße 16");
        var addr2 = new Address("5020", "Salzburg", "Bahnhofstraße 29");
        var cust1 = new Customer("Max Mustermann", "max@muster.com", addr1, addr1);
        var method = new Bankaccount(cust1, "AT47700000000000", "ATSBGRAI09");

        System.out.println("######### add customer #########");
        customerDao.insert(cust1);
        JpaUtil.commit();

        System.out.println("######### print customer #########");
        customerDao.getAll().forEach(customer -> System.out.println(customer));

        System.out.println("######### add payment method #########");
        cust1.addPaymentMethod(method);
        cust1 = customerDao.update(cust1);
        JpaUtil.commit();

        System.out.println("######### change address #########");
        cust1.setShippingAddress(addr2);
        cust1 = customerDao.update(cust1);
        JpaUtil.commit();

        System.out.println("######### print customer #########");
        customerDao.getAll().forEach(customer -> System.out.println(customer));

        System.out.println("######### remove payment method #########");
        cust1.removePaymentMethod(method);
        cust1 = customerDao.update(cust1);
        //DaoFactory.getPaymentMethodDao().remove(method);
        JpaUtil.commit();

        System.out.println("######### print customer #########");
        customerDao.getAll().forEach(customer -> System.out.println(customer));

        System.out.println("######### get customer by mail #########");
        System.out.println(customerDao.getByEmail("max@muster.com"));
        JpaUtil.commit();

        System.out.println("######### delete customer #########");
        customerDao.remove(cust1);
        JpaUtil.commit();
    }
}

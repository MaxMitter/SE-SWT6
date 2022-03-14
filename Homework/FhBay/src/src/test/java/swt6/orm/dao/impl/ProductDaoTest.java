package swt6.orm.dao.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import swt6.orm.dao.DaoFactory;
import swt6.orm.dao.ProductDao;
import swt6.orm.domain.Bid;
import swt6.orm.domain.Customer;
import swt6.orm.domain.Product;
import swt6.orm.domain.ProductStatus;
import swt6.util.JpaUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    private static final Customer testCustomer = new Customer("Test", "test@mail.com", null, null);

    private static final ProductDao productDao = DaoFactory.getProductDao();

    @BeforeAll
    static void setUp() {
        DaoFactory.getCustomerDao().insert(testCustomer);
    }

    @AfterEach
    void tearDown() {
        JpaUtil.resetTableForTests("Bid");
        JpaUtil.resetTableForTests("Product");
    }

    @AfterAll
    static void afterAll() {
        JpaUtil.resetDatabase();
    }

    @Test
    void getById() {
        var product = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product);

        var result = productDao.getById(product.getId());

        assertEquals(product.getId(), result.getId());
    }

    @Test
    void insert() {
        var product = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product);

        assertNotNull(product.getId());
    }

    @Test
    void update() {
        var product = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product);
        var newName = "Other Product";

        product.setName(newName);
        product = productDao.update(product);

        assertEquals(newName, product.getName());
    }

    @Test
    void remove() {
        var product = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product);
        productDao.remove(product);

        var result = productDao.getById(product.getId());

        assertNull(result);
    }

    @Test
    void getAll() {
        var product1 = new Product("Testproduct 1", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        var product2 = new Product("Testproduct 2", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product1);
        productDao.insert(product2);

        var result = productDao.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getByName() {
        var product1 = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        var product2 = new Product("Other product", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product1);
        productDao.insert(product2);

        var result = productDao.getByName("Test");

        assertEquals(1, result.size());
        assertEquals(product1.getId(), result.get(0).getId());
    }

    @Test
    void getByDescription() {
        var product1 = new Product("Product", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        var product2 = new Product("Other product", "this is used for production", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product1);
        productDao.insert(product2);

        var result = productDao.getByNameOrDescription("test");

        assertEquals(1, result.size());
        assertEquals(product1.getId(), result.get(0).getId());
    }

    @Test
    void getByStatus() {
        var product1 = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        var product2 = new Product("Other product", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.NOT_SELLABLE);
        productDao.insert(product1);
        productDao.insert(product2);

        var result = productDao.getByStatus(ProductStatus.NOT_SELLABLE);

        assertEquals(1, result.size());
        assertEquals(product2.getId(), result.get(0).getId());
    }

    @Test
    void getBySeller() {
        var product1 = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        var product2 = new Product("Other product", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), null, null, ProductStatus.SAVED);
        productDao.insert(product1);
        productDao.insert(product2);

        var result = productDao.getBySeller(testCustomer);

        assertEquals(1, result.size());
        assertEquals(product1.getId(), result.get(0).getId());
    }

    @Test
    void getByBuyer() {
        var product1 = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        var product2 = new Product("Other product", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, testCustomer, ProductStatus.SAVED);
        productDao.insert(product1);
        productDao.insert(product2);

        var result = productDao.getByBuyer(testCustomer);

        assertEquals(1, result.size());
        assertEquals(product2.getId(), result.get(0).getId());
    }
<<<<<<< HEAD:Homework/FhBay/src/src/test/java/swt6/orm/dao/impl/ProductDaoTest.java
=======

    void generateBids(Product product) {
        var otherCustomer = new Customer("Other", "other@mail.com", null, null);
        var bid1 = new Bid(100, LocalDateTime.now(), otherCustomer, product);
        var bid2 = new Bid(110, LocalDateTime.now(), otherCustomer, product);
        var bid3 = new Bid(120, LocalDateTime.now(), otherCustomer, product);
        var bid4 = new Bid(130, LocalDateTime.now(), otherCustomer, product);
        var bid5 = new Bid(140, LocalDateTime.now(), testCustomer, product);

        DaoFactory.getCustomerDao().insert(otherCustomer);
        DaoFactory.getBidDao().insert(bid1);
        DaoFactory.getBidDao().insert(bid2);
        DaoFactory.getBidDao().insert(bid3);
        DaoFactory.getBidDao().insert(bid4);
        DaoFactory.getBidDao().insert(bid5);
    }

    @Test
    void finalizeProductTest() {
        var product = new Product("Bidding Product", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);
        productDao.insert(product);
        generateBids(product);

        product = productDao.finalizeBidProcess(product);

        assertEquals(130, product.getFinalBid());
        assertEquals(ProductStatus.SOLD, product.getStatus());
        assertEquals(testCustomer, product.getBuyer());
    }
>>>>>>> 49e2e40... Adds test for finalizeBid but doesn't work:Homework/FhBay/src/test/java/swt6/orm/dao/impl/ProductDaoTest.java
}
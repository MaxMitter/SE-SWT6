package swt6.orm.dao.impl;

import org.junit.jupiter.api.*;
import swt6.orm.dao.BidDao;
import swt6.orm.dao.DaoFactory;
import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BidDaoTest {

    private static final Customer testCustomer = new Customer("Test", "test@mail.com", null, null);
    private static final Product testProduct = new Product("Testproduct", "this is used for testing", 100.0,
            150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), testCustomer, null, ProductStatus.SAVED);

    private final BidDao bidDao = DaoFactory.getBidDao();

    @BeforeAll
    static void setUp() {
        DaoFactory.getCustomerDao().insert(testCustomer);
        DaoFactory.getProductDao().insert(testProduct);
    }

    @AfterEach
    void tearDown() {
        JpaUtil.resetTableForTests("Bid");
    }

    @AfterAll
    static void cleanUp() {
        JpaUtil.resetDatabase();
    }

    @Test
    void getById() {
        var bid = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);
        bidDao.insert(bid);

        var result = bidDao.getById(bid.getId());

        assertEquals(bid.getId(), result.getId());
    }

    @Test
    void insert() {
        var bid = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);
        bidDao.insert(bid);

        assertNotNull(bid.getId());
    }

    @Test
    void update() {
        var bid = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);
        bidDao.insert(bid);
        var newBidValue = 200.0;

        bid.setValue(newBidValue);
        bid = bidDao.update(bid);

        assertEquals(newBidValue, bid.getValue());
    }

    @Test
    void remove() {
        var bid = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);
        bidDao.insert(bid);
        bidDao.remove(bid);

        var result = bidDao.getById(bid.getId());

        assertNull(result);
    }

    @Test
    void getAll() {
        var bid1 = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);
        var bid2 = new Bid(200.0, LocalDateTime.now(), testCustomer, testProduct);
        bidDao.insert(bid1);
        bidDao.insert(bid2);

        var result = bidDao.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getHighestBid() {
        var bid1 = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);
        var bid2 = new Bid(200.0, LocalDateTime.now(), testCustomer, testProduct);
        bidDao.insert(bid1);
        bidDao.insert(bid2);

        var result = bidDao.getHighestBidByProductId(testProduct.getId());

        assertEquals(200.0, result.getValue());
    }

    @Test
    void getBidsByProductId() {
        var bid1 = new Bid(100.0, LocalDateTime.now(), testCustomer, testProduct);
        var bid2 = new Bid(200.0, LocalDateTime.now(), testCustomer, testProduct);
        var prod = new Product();
        DaoFactory.getProductDao().insert(prod);
        var bid3 = new Bid(200.0, LocalDateTime.now(), testCustomer, prod);
        bidDao.insert(bid1);
        bidDao.insert(bid2);
        bidDao.insert(bid3);

        var result = bidDao.getBidsByProductId(testProduct.getId());
        var all = bidDao.getAll();

        assertEquals(2, result.size());
        assertEquals(3, all.size());
    }
}
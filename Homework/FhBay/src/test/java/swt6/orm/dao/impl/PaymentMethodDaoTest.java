package swt6.orm.dao.impl;

import org.junit.jupiter.api.*;
import swt6.orm.dao.DaoFactory;
import swt6.orm.dao.PaymentMethodDao;
import swt6.orm.domain.Bankaccount;
import swt6.orm.domain.CreditCard;
import swt6.orm.domain.Customer;
import swt6.orm.domain.PaymentMethod;
import swt6.util.JpaUtil;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodDaoTest {

    private static final Customer testCustomer = new Customer("Test", "test@mail.com", null, null);

    private static final PaymentMethodDao paymentMethodDao = DaoFactory.getPaymentMethodDao();

    @BeforeAll
    static void setUp() {
        DaoFactory.getCustomerDao().insert(testCustomer);
    }

    @AfterEach
    void tearDown() {
        JpaUtil.resetTableForTests("PaymentMethod");
    }

    @AfterAll
    static void afterAll() {
        JpaUtil.resetDatabase();
    }

    @Test
    void getById() {
        var method = new Bankaccount(testCustomer, "AT4700000000000", "ATSBGRAI09");
        paymentMethodDao.insert(method);

        var result = paymentMethodDao.getById(method.getId());

        assertEquals(method.getId(), result.getId());
    }

    @Test
    void insertBankaccount() {
        var method = new Bankaccount(testCustomer, "AT4700000000000", "ATSBGRAI09");
        paymentMethodDao.insert(method);

        var result = paymentMethodDao.getById(method.getId());

        assertEquals(Bankaccount.class, result.getClass());
    }

    @Test
    void insertCard() {
        var method = new CreditCard(testCustomer, "5455268495843", "11/22", "314");
        paymentMethodDao.insert(method);

        var result = paymentMethodDao.getById(method.getId());

        assertEquals(CreditCard.class, result.getClass());
    }

    @Test
    void update() {
        var method = new Bankaccount(testCustomer, "AT4700000000000", "ATSBGRAI09");
        paymentMethodDao.insert(method);
        var newIban = "AT01000000000000";

        method.setIban(newIban);
        method = (Bankaccount)paymentMethodDao.update(method);

        assertEquals(newIban, method.getIban());
    }

    @Test
    void remove() {
        var method = new Bankaccount(testCustomer, "AT4700000000000", "ATSBGRAI09");
        paymentMethodDao.insert(method);
        paymentMethodDao.remove(method);

        var result = paymentMethodDao.getById(method.getId());

        assertNull(result);
    }

    @Test
    void getAll() {
        var method1 = new Bankaccount(testCustomer, "AT4700000000000", "ATSBGRAI09");
        var method2 = new CreditCard(testCustomer, "5455268495843", "11/22", "314");
        paymentMethodDao.insert(method1);
        paymentMethodDao.insert(method2);

        var result = paymentMethodDao.getAll();

        assertEquals(2, result.size());
    }
}
package swt6.orm.dao.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import swt6.orm.dao.CustomerDao;
import swt6.orm.dao.DaoFactory;
import swt6.orm.domain.Address;
import swt6.orm.domain.Customer;
import swt6.util.JpaUtil;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoTest {

    private CustomerDao customerDao = DaoFactory.getCustomerDao();

    @AfterEach
    void afterEach() {
        JpaUtil.resetTableForTests("Customer");
    }

    @AfterAll
    static void cleanUp() {
        JpaUtil.resetDatabase();
    }

    @Test
    void getById() {
        var addr = new Address("4040", "Linz", "Teststraße");
        var customer = new Customer("Test Name", "test@name.com", addr, addr);
        customerDao.insert(customer);

        var result = customerDao.getById(customer.getId());

        assertEquals(customer.getId(), result.getId());
    }

    @Test
    void insert() {
        var addr = new Address("4040", "Linz", "Teststraße");
        var customer = new Customer("Test Name", "test@name.com", addr, addr);
        customerDao.insert(customer);

        assertNotNull(customer.getId());
    }

    @Test
    void update() {
        var addr = new Address("4040", "Linz", "Teststraße");
        var customer = new Customer("Test Name", "test@name.com", addr, addr);
        var newName = "Testing Name";
        customerDao.insert(customer);

        customer.setName(newName);

        customer = customerDao.update(customer);

        assertEquals(newName, customer.getName());
    }

    @Test
    void remove() {
        var addr = new Address("4040", "Linz", "Teststraße");
        var customer = new Customer("Test Name", "test@name.com", addr, addr);
        customerDao.insert(customer);
        customerDao.remove(customer);

        var result = customerDao.getById(customer.getId());

        assertNull(result);
    }

    @Test
    void getAll() {
        var addr = new Address("4040", "Linz", "Teststraße");
        var customer1 = new Customer("Test Name", "test@name.com", addr, addr);
        var customer2 = new Customer("Test Name 2", "test@name.com", addr, addr);
        customerDao.insert(customer1);
        customerDao.insert(customer2);

        var result = customerDao.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void getByEmail() {
        var addr = new Address("4040", "Linz", "Teststraße");
        var customer = new Customer("Test Name", "test@name.com", addr, addr);
        customerDao.insert(customer);

        var result = customerDao.getByEmail("test@name.com");

        assertEquals(customer.getId(), result.getId());
    }
}
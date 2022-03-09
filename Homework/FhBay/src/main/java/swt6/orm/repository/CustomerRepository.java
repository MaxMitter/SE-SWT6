package swt6.orm.repository;

import swt6.orm.domain.Address;
import swt6.orm.domain.Customer;
import swt6.util.CrudUtil;
import swt6.util.JpaUtil;

public class CustomerRepository {
    public void createCustomer(Customer customer) {
        CrudUtil.insertEntity(customer);
    }

    public void updateShippingAddress(Customer customer, Address newAddress) {
        customer.setShippingAddress(newAddress);
        CrudUtil.saveEntity(customer);
    }

    public void updateBillingAddress(Customer customer, Address newAddress) {
        customer.setBillingAddress(newAddress);
        CrudUtil.saveEntity(customer);
    }
}

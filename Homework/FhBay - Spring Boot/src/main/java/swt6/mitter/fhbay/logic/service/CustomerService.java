package swt6.mitter.fhbay.logic.service;

import swt6.mitter.fhbay.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    Customer searchCustomerByName(String substring);
}

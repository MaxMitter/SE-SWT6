package swt6.mitter.fhbay.logic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.logic.service.CustomerService;
import swt6.mitter.fhbay.repository.CustomerRepository;

import java.util.List;

@Service("customerService")
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = false)
    public Customer save(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer searchCustomerByName(String substring) {
        var cust = customerRepository.findByNameLikeIgnoreCase(substring);
        return cust.orElse(null);
    }
}

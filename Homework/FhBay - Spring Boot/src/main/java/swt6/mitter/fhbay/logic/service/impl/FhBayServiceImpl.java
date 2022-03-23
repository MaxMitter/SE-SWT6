package swt6.mitter.fhbay.logic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.mitter.fhbay.domain.Bid;
import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.domain.PaymentMethod;
import swt6.mitter.fhbay.domain.Product;
import swt6.mitter.fhbay.logic.service.FhBayService;
import swt6.mitter.fhbay.repository.BidRepository;
import swt6.mitter.fhbay.repository.CustomerRepository;
import swt6.mitter.fhbay.repository.PaymentMethodRepository;
import swt6.mitter.fhbay.repository.ProductRepository;

@Service("initService")
@Transactional
public class FhBayServiceImpl implements FhBayService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public Customer syncCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Bid syncBid(Bid bid) {
        return bidRepository.saveAndFlush(bid);
    }

    @Override
    public Product syncProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public PaymentMethod syncPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethodRepository.saveAndFlush(paymentMethod);
    }
}

package swt6.mitter.fhbay.logic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import swt6.mitter.fhbay.domain.Bid;
import swt6.mitter.fhbay.domain.ProductStatus;
import swt6.mitter.fhbay.exceptions.BiddingNotOpenException;
import swt6.mitter.fhbay.logic.service.BidService;
import swt6.mitter.fhbay.repository.BidRepository;
import swt6.mitter.fhbay.repository.CustomerRepository;
import swt6.mitter.fhbay.repository.ProductRepository;

import java.time.LocalDateTime;

public class BidServiceImpl implements BidService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BidRepository bidRepository;

    @Override
    public Bid addBid(long productId, long userId, double value) throws BiddingNotOpenException {
        var product = productRepository.getById(productId);
        var user = customerRepository.getById(userId);

        if (product.getStatus() == ProductStatus.OPEN_FOR_BIDS) {
            var bid = new Bid(value, LocalDateTime.now(), user, product);
            return bidRepository.saveAndFlush(bid);
        } else {
            throw new BiddingNotOpenException("Can not bid on this product, status is " + product.getStatus());
        }
    }
}

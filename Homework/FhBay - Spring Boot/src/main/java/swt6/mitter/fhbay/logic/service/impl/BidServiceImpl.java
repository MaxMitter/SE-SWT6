package swt6.mitter.fhbay.logic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import swt6.mitter.fhbay.domain.Bid;
import swt6.mitter.fhbay.domain.Product;
import swt6.mitter.fhbay.domain.ProductStatus;
import swt6.mitter.fhbay.exceptions.BiddingNotOpenException;
import swt6.mitter.fhbay.logic.service.BidService;
import swt6.mitter.fhbay.repository.BidRepository;
import swt6.mitter.fhbay.repository.CustomerRepository;
import swt6.mitter.fhbay.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private Optional<Bid> getHighestBid(Product p) {
        var allBids = bidRepository.findByArticleEquals(p);
        if (allBids.size() == 0)
            return Optional.empty();
        else if (allBids.size() == 1) {
            allBids = allBids.stream().sorted((bid1, bid2) -> (int) (bid2.getValue() - bid1.getValue())).toList();
            return Optional.ofNullable(allBids.get(0));
        } else {
            allBids.get(0).setValue(allBids.get(1).getValue());
            return Optional.ofNullable(allBids.get(0));
        }
    }

    @Override
    public void finalizeBidding(Product product) {
        var finalBid = getHighestBid(product);
        if (finalBid.isPresent()) {
            var bid = finalBid.get();
            product.setBuyer(bid.getBuyer());
            product.setFinalBid(bid.getValue());
            product.setStatus(ProductStatus.SOLD);
        } else {
            product.setStatus(ProductStatus.CLOSED_NO_BIDS);
        }

        productRepository.saveAndFlush(product);
    }

    @Scheduled(fixedDelay = 5000L)
    public void checkAuctionStatus() {
        for (var product : productRepository.findAuctionsStatusOpenButExpired()) {
            finalizeBidding(product);
        }
    }
}

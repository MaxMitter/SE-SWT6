package swt6.mitter.fhbay.logic.service;

import swt6.mitter.fhbay.domain.Bid;
import swt6.mitter.fhbay.exceptions.BiddingNotOpenException;

public interface BidService {
    Bid addBid(long productId, long userId, double value) throws BiddingNotOpenException;
}

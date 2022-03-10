package swt6.orm.dao;

import swt6.orm.domain.Bid;
import swt6.orm.domain.Product;

public interface BidDao extends BaseDao<Bid> {
    public Bid getHighestBid(Product product);
}

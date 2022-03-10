package swt6.orm.dao;

import swt6.orm.domain.Bid;
import swt6.orm.domain.Product;

import java.util.List;

public interface BidDao extends BaseDao<Bid> {
    Bid getHighestBid(Long id);
    List<Bid> getBidsByProductId(Long id);
}

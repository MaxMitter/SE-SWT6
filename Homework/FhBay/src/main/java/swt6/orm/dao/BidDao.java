package swt6.orm.dao;

import swt6.orm.domain.Bid;

import java.util.List;

public interface BidDao extends BaseDao<Bid> {
    Bid getHighestBidByProductId(Long id);
    List<Bid> getBidsByProductId(Long id);
}

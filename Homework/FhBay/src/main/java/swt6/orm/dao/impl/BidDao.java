package swt6.orm.dao.impl;

import swt6.orm.domain.Bid;
import swt6.util.JpaUtil;

import java.util.List;

public class BidDao extends BaseDao<Bid> implements swt6.orm.dao.BidDao {
    @Override
    protected Class<Bid> getType() {
        return Bid.class;
    }

    @Override
    public List<Bid> getAll() {
        var em = JpaUtil.getTransactedEntityManager();
        return em.createQuery("select b from Bid b", getType()).getResultList();
    }
}

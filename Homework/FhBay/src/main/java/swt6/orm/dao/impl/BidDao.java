package swt6.orm.dao.impl;

import swt6.orm.domain.Bid;
import swt6.orm.domain.Product;
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

    @Override
    public Bid getHighestBid(Long articleId) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("select b from Bid b where Article.Id = :productId order by Value desc", Bid.class);
        query.setParameter("productId", articleId);
        var result = query.getResultList();

        if (result.size() == 0)
            return null;

        return result.get(0);
    }

    @Override
    public List<Bid> getBidsByProductId(Long id) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("select b from Bid b where Article.Id = :productId order by Value desc", Bid.class);
        query.setParameter("productId", id);
        return query.getResultList();
    }
}

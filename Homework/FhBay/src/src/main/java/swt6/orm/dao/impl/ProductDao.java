package swt6.orm.dao.impl;

import swt6.orm.domain.Bid;
import swt6.orm.domain.Customer;
import swt6.orm.domain.Product;
import swt6.orm.domain.ProductStatus;
import swt6.util.JpaUtil;

import java.util.List;

public class ProductDao extends BaseDao<Product> implements swt6.orm.dao.ProductDao {

    @Override
    protected Class<Product> getType() {
        return Product.class;
    }

    @Override
    public List<Product> getAll() {
        var em = JpaUtil.getTransactedEntityManager();
        return em.createQuery("select p from Product p", getType()).getResultList();
    }

    @Override
    public List<Product> getByName(String name) {
        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        query.select(entry).where(cb.like(cb.lower(entry.get("Name")), "%" + name.toLowerCase() + "%"));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> getByNameOrDescription(String searchTerm) {
        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        var predName = cb.like(cb.lower(entry.get("Name")), "%" + searchTerm.toLowerCase() + "%");
        var predDesc = cb.like(cb.lower(entry.get("Description")), "%" + searchTerm.toLowerCase() + "%");

        query.select(entry).where(cb.or(predName, predDesc));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> getByStatus(ProductStatus status) {
        if (status == null)
            throw new IllegalArgumentException("Status cannot be null");

        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        query.select(entry).where(cb.equal(entry.get("Status"), status.ordinal()));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> getBySeller(Customer seller) {
        if (seller == null)
            throw new IllegalArgumentException("Seller cannot be null");

        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        query.select(entry).where(cb.equal(entry.get("Seller"), seller.getId()));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> getByBuyer(Customer buyer) {
        if (buyer == null)
            throw new IllegalArgumentException("Buyer cannot be null");

        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        query.select(entry).where(cb.equal(entry.get("Buyer"), buyer.getId()));

        return em.createQuery(query).getResultList();
    }

    @Override
    public Product finalizeBidProcess(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null");

        try {
            var em = JpaUtil.getTransactedEntityManager();
            var query = em.createQuery("select b from Bid b where Product = :product order by Value desc", Bid.class);
            query.setParameter("product", product);
            query.setMaxResults(2);
            var result = query.getResultList();

            if (result != null && result.size() >= 2) {
                var firstBid = result.get(0);
                var secondBid = result.get(1);

                product.setFinalBid(secondBid.getValue());
                product.setBuyer(firstBid.getBuyer());
                product.setStatus(ProductStatus.SOLD);

                product = em.merge(product);
            } else if (result != null && result.size() == 1) {
                var firstBid = result.get(0);

                product.setFinalBid(firstBid.getValue());
                product.setBuyer(firstBid.getBuyer());
                product.setStatus(ProductStatus.SOLD);

                product = em.merge(product);
            } else {
                throw new RuntimeException("Not enough Bids found, product could not be sold");
            }

            return product;
        } catch (Exception ex) {
            JpaUtil.rollback();
            throw ex;
        }
    }
}

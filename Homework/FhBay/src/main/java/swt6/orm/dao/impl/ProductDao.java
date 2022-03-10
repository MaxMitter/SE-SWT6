package swt6.orm.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.internal.SessionFactoryBuilderImpl;
import swt6.orm.domain.Customer;
import swt6.orm.domain.Product;
import swt6.orm.domain.ProductStatus;
import swt6.util.JpaUtil;

import java.util.List;
import java.util.Locale;

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
        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        query.select(entry).where(cb.equal(entry.get("Status"), status.ordinal()));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> getBySeller(Customer seller) {
        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        query.select(entry).where(cb.equal(entry.get("Seller"), seller.getId()));

        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Product> getByBuyer(Customer buyer) {
        var em = JpaUtil.getTransactedEntityManager();
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Product.class);
        var entry = query.from(Product.class);

        query.select(entry).where(cb.equal(entry.get("Buyer"), buyer.getId()));

        return em.createQuery(query).getResultList();
    }
}

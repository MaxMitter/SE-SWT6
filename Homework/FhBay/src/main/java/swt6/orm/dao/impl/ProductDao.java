package swt6.orm.dao.impl;

import swt6.orm.domain.Product;
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
}

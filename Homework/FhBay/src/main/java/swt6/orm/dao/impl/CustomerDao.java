package swt6.orm.dao.impl;

import swt6.orm.domain.Customer;
import swt6.util.JpaUtil;

import java.util.List;

public class CustomerDao extends BaseDao<Customer> implements swt6.orm.dao.CustomerDao {
    @Override
    protected Class<Customer> getType() {
        return Customer.class;
    }

    @Override
    public List<Customer> getAll() {
        var em = JpaUtil.getTransactedEntityManager();
        return em.createQuery("select c from Customer c", getType()).getResultList();
    }

    @Override
    public Customer getByEmail(String email) {
        var em = JpaUtil.getTransactedEntityManager();
        var query = em.createQuery("select c from Customer c where Email = :email", Customer.class);
        query.setParameter("email", email);
        var result = query.getResultList();
        if (result.size() == 0)
            return null;
        else if (result.size() > 1)
            throw new IllegalStateException("More than one customer with the same email found!");

        return result.get(0);
    }
}

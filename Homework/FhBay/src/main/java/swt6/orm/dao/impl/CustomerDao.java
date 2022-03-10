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
}

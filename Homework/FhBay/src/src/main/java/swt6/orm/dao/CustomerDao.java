package swt6.orm.dao;

import swt6.orm.domain.Customer;

public interface CustomerDao extends BaseDao<Customer> {
    Customer getByEmail(String email);
}

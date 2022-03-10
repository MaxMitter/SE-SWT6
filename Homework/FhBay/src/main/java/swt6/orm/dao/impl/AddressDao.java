package swt6.orm.dao.impl;

import swt6.orm.domain.Address;
import swt6.util.JpaUtil;

import java.util.List;

public class AddressDao extends BaseDao<Address> implements swt6.orm.dao.AddressDao {
    @Override
    protected Class<Address> getType() {
        return Address.class;
    }

    @Override
    public List<Address> getAll() {
        var em = JpaUtil.getTransactedEntityManager();
        return em.createQuery("select a from Address a", getType()).getResultList();
    }
}

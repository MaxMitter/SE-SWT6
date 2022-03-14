package swt6.orm.dao.impl;

import swt6.orm.domain.PaymentMethod;
import swt6.util.JpaUtil;

import java.util.List;

public class PaymentMethodDao extends BaseDao<PaymentMethod> implements swt6.orm.dao.PaymentMethodDao {
    @Override
    protected Class<PaymentMethod> getType() {
        return PaymentMethod.class;
    }

    @Override
    public List<PaymentMethod> getAll() {
        var em = JpaUtil.getTransactedEntityManager();
        return em.createQuery("select p from PaymentMethod p", getType()).getResultList();
    }
}

package swt6.orm.repository;

import swt6.orm.domain.PaymentMethod;
import swt6.util.JpaUtil;

public class PaymentMethodRepository {
    public void addPaymentMethod(PaymentMethod method) {
        try {
            var em = JpaUtil.getTransactedEntityManager();

            em.persist(method);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
    }
}

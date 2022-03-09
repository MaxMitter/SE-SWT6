package swt6.orm.repository;

import swt6.orm.domain.*;
import swt6.util.JpaUtil;

public class PaymentMethodRepository {
    public Customer addPaymentMethod(Customer customer, PaymentMethod method) {
        try {
            var em = JpaUtil.getTransactedEntityManager();

            customer.addPaymentMethod(method);
            customer = em.merge(customer);

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
        return customer;
    }

    public void removePaymentMethod(Customer customer, PaymentMethod method) {
        try {
            var em = JpaUtil.getTransactedEntityManager();

            if (customer.removePaymentMethod(method)) {
                em.remove(method);
            } else throw new IllegalArgumentException("Payment Method not connected to the selected user");

            JpaUtil.commit();
        } catch (Exception ex) {
            JpaUtil.rollback();
        }
    }
}

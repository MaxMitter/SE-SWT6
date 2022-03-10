package swt6.orm.dao;

public class DaoFactory {
    private static AddressDao addressDao;
    private static BidDao bidDao;
    private static CustomerDao customerDao;
    private static PaymentMethodDao paymentMethodDao;
    private static ProductDao productDao;

    public static AddressDao getAddressDao() {
        if (addressDao == null)
            addressDao = new swt6.orm.dao.impl.AddressDao();
        return addressDao;
    }

    public static BidDao getBidDao() {
        if (bidDao == null)
            bidDao = new swt6.orm.dao.impl.BidDao();
        return bidDao;
    }

    public static CustomerDao getCustomerDao() {
        if (customerDao == null)
            customerDao = new swt6.orm.dao.impl.CustomerDao();
        return customerDao;
    }

    public static PaymentMethodDao getPaymentMethodDao() {
        if (paymentMethodDao == null)
            paymentMethodDao = new swt6.orm.dao.impl.PaymentMethodDao();
        return paymentMethodDao;
    }

    public static ProductDao getProductDao() {
        if (productDao == null)
            productDao = new swt6.orm.dao.impl.ProductDao();
        return productDao;
    }
}

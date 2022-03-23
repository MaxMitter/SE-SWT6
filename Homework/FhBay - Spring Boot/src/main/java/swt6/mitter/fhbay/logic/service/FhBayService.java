package swt6.mitter.fhbay.logic.service;

import swt6.mitter.fhbay.domain.*;

public interface FhBayService {
    // ######## Customers ######### //
    Customer syncCustomer(Customer customer);

    // ######## Bids ######### //
    Bid syncBid(Bid bid);

    // ######## Products ######### //
    Product syncProduct(Product product);

    // ######## Payment Method ######### //
    PaymentMethod syncPaymentMethod(PaymentMethod paymentMethod);
}

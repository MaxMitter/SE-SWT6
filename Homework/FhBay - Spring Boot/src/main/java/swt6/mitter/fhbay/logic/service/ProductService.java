package swt6.mitter.fhbay.logic.service;

import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.domain.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product addSellerToProduct(Product product, Customer seller);
    Product addBuyerToProduct(Product product, Customer buyer);

    List<Product> findAll();

    List<Product> findByName(String pattern);
    List<Product> findByDescription(String pattern);
    List<Product> findByNameOrDescription(String pattern);
}

package swt6.mitter.fhbay.logic.service;

import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.domain.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    Product findById(long productId);
    Product addSellerToProduct(long productId, long sellerId);
    Product addBuyerToProduct(long productId, long buyerId);

    List<Product> findAll();

    List<Product> findByName(String pattern);
    List<Product> findByDescription(String pattern);
    List<Product> findByNameOrDescription(String pattern);
}

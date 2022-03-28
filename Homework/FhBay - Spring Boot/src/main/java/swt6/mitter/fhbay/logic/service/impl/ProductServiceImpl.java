package swt6.mitter.fhbay.logic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.domain.Product;
import swt6.mitter.fhbay.logic.service.ProductService;
import swt6.mitter.fhbay.repository.CustomerRepository;
import swt6.mitter.fhbay.repository.ProductRepository;

import java.util.List;

@Service("productService")
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = false)
    public Product save(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product findById(long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Product addSellerToProduct(long productId, long sellerId) {
        var product = productRepository.getById(productId);
        var seller = customerRepository.getById(sellerId);

        product.setSeller(seller);
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product addBuyerToProduct(long productId, long buyerId) {
        var product = productRepository.getById(productId);
        var buyer = customerRepository.getById(buyerId);

        product.setBuyer(buyer);
        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByName(String pattern) {
        return productRepository.findByNameContainsIgnoreCase(pattern);
    }

    @Override
    public List<Product> findByDescription(String pattern) {
        return productRepository.findByDescriptionContainsIgnoreCase(pattern);
    }

    @Override
    public List<Product> findByNameOrDescription(String pattern) {
        return productRepository.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(pattern, pattern);
    }
}

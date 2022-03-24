package swt6.mitter.fhbay.logic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.domain.Product;
import swt6.mitter.fhbay.logic.service.ProductService;
import swt6.mitter.fhbay.repository.ProductRepository;

import java.util.List;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product addSellerToProduct(Product product, Customer seller) {
        product.setSeller(seller);
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product addBuyerToProduct(Product product, Customer buyer) {
        product.setBuyer(buyer);
        return productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional(readOnly = true)
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

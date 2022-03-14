package swt6.orm.dao;

import swt6.orm.domain.Customer;
import swt6.orm.domain.Product;
import swt6.orm.domain.ProductStatus;

import java.util.List;

public interface ProductDao extends BaseDao<Product> {
    List<Product> getByName(String name);
    List<Product> getByNameOrDescription(String searchTerm);
    List<Product> getByStatus(ProductStatus status);
    List<Product> getBySeller(Customer seller);
    List<Product> getByBuyer(Customer buyer);
    Product finalizeBidProcess(Product product);
}

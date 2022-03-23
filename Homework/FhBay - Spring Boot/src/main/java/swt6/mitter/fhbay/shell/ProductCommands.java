package swt6.mitter.fhbay.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import swt6.mitter.fhbay.domain.Product;
import swt6.mitter.fhbay.domain.ProductStatus;
import swt6.mitter.fhbay.logic.service.ProductService;

import java.time.LocalDateTime;

@ShellComponent
public class ProductCommands {

    @Autowired
    private ProductService productService;

    @ShellMethod("Creates a random Product for testing")
    public String createRandomProduct() {
        Product testProduct = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), null, null, ProductStatus.SAVED);

        testProduct = productService.createProduct(testProduct);
        return testProduct.toString();
    }

    @ShellMethod("Finds all products in DB")
    public String getAllProducts() {
        return productService.findAll().toString();
    }
}

package swt6.mitter.fhbay.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.mitter.fhbay.domain.Product;
import swt6.mitter.fhbay.domain.ProductStatus;
import swt6.mitter.fhbay.logic.service.BidService;
import swt6.mitter.fhbay.logic.service.ProductService;

import java.time.LocalDateTime;

@ShellComponent
public class ProductCommands {

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @ShellMethod("Creates a random Product for testing")
    public String createRandomProduct() {
        Product testProduct = new Product("Testproduct", "this is used for testing", 100.0,
                150.0, LocalDateTime.now(), LocalDateTime.now().plusDays(1), null, null, ProductStatus.OPEN_FOR_BIDS);

        testProduct = productService.createProduct(testProduct);
        return testProduct.toString();
    }

    @ShellMethod("Finds all products in DB")
    public String getAllProducts() {
        return productService.findAll().toString();
    }

    @ShellMethod("Gets all products filtered by name or description")
    public String getProducts(
            @ShellOption(defaultValue = "") String name,
            @ShellOption(defaultValue = "") String description) {

        if (name.length() == 0 && description.length() == 0)
            return "Please provide either a name, a description or both.";
        else if (name.length() == 0)
            return productService.findByDescription(description).toString();
        else
            return productService.findByName(name).toString();
    }

    @ShellMethod("Finds all products with the given String in either name or description")
    public String findProducts(String searchValue) {
            return productService.findByNameOrDescription(searchValue).toString();
    }

    @ShellMethod("Adds a given Buyer to a given Product")
    public String addBuyer(long productId, long userId) {
        productService.addBuyerToProduct(productId, userId);
        return "Buyer successfully added";
    }

    @ShellMethod("Adds a given Seller to a given Product")
    public String addSeller(long productId, long userId) {
        productService.addSellerToProduct(productId, userId);
        return "Seller successfully added";
    }

    @ShellMethod("Manually finalized the Sale of a product")
    public String finalizeSale(long productId, boolean force) {
        var product = productService.findById(productId);
        if (product == null) return "Product with ID " + productId + "could not be found.";

        try {
            bidService.finalizeBidding(product);
        } catch (Exception ex) {
            return "Finalzising failed";
        }
        return "Product sale finalized.";
    }
}

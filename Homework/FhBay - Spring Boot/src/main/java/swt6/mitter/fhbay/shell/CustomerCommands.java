package swt6.mitter.fhbay.shell;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import swt6.mitter.fhbay.domain.Address;
import swt6.mitter.fhbay.domain.Customer;
import swt6.mitter.fhbay.logic.service.CustomerService;

@ShellComponent
public class CustomerCommands {

    @Autowired
    @Lazy
    private LineReader reader;

    @Autowired
    private CustomerService customerService;

    @ShellMethod("Prints all customers")
    public String getAllCustomers() {
        return customerService.getAllCustomers().toString();
    }

    @ShellMethod("Gets Customer by Id")
    public String get(long customerId) {
        return customerService.findById(customerId).toString();
    }

    @ShellMethod("Creates a new Customer")
    public String newCustomer() {
        var name = reader.readLine("Name > ");
        var email = reader.readLine("Email > ");

        var shippAddressZip = reader.readLine("Shipping Address Zip-Code > ");
        var shippAddressCity = reader.readLine("Shipping Address City > ");
        var shippAddressStreet = reader.readLine("Shipping Address Street > ");
        var shippAddress = new Address(shippAddressZip, shippAddressCity, shippAddressStreet);

        Address paymentAddress = null;
        var useShipAddr = reader.readLine("Use this Address as Payment Address? ([yes]/no) >");
        if (useShipAddr.equals("yes") || useShipAddr.length() == 0)
            paymentAddress = shippAddress;
        else {
            var payAddressZip = reader.readLine("Payment Address Zip-Code > ");
            var payAddressCity = reader.readLine("Payment Address City > ");
            var payAddressStreet = reader.readLine("Payment Address Street > ");
            paymentAddress = new Address(payAddressZip, payAddressCity, payAddressStreet);
        }

        var cust = new Customer(name, email, shippAddress, paymentAddress);
        return customerService.save(cust).toString();
    }
}

package swt6.mitter.fhbay.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import swt6.mitter.fhbay.logic.service.CustomerService;

@ShellComponent
public class CustomerCommands {

    @Autowired
    private CustomerService customerService;

    @ShellMethod("Prints all customers")
    public String getAllCustomers() {
        return customerService.getAllCustomers().toString();
    }

    @ShellMethod("Gets Customer by Id")
    public String get(long customerId) {
        return customerService.getCustomerById(customerId).toString();
    }
}

package swt6.mitter.fhbay.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CustomerCommands {

    @ShellMethod("Prints all customers")
    public String printall() {
        return "this works";
    }
}

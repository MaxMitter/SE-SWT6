package swt6.spring.basics.hello;

public class GreetingServiceImpl implements GreetingService {

    private String message = "Default Message";

    @Override
    public void sayHello() {
        System.out.println(getMessage());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void destroy() {
        System.out.println("GreetingServiceImpl.destroy()");
    }
}

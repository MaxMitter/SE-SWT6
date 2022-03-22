package swt6.spring.basics.hello;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.ioc.logic.WorkLogService;
import swt6.spring.basics.ioc.logic.factorybased.WorkLogServiceImpl;

public class GreetingClient {
    public static void main(String[] args) {
        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext("swt6/spring/basics/hello/greetingConfig.xml")
        ) {
            GreetingService service = factory.getBean(GreetingService.class);
            service.sayHello();
        } //Auto-Closeable

    }
}

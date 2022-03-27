package swt6.mitter.fhbay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FhBayApplication {
    public static void main(String[] args) {
        SpringApplication.run(FhBayApplication.class, args);
    }
}

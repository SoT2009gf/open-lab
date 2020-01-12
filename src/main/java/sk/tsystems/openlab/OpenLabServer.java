package sk.tsystems.openlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class OpenLabServer {
    public static void main(String[] args) {
        SpringApplication.run(OpenLabServer.class, args);
    }    
}
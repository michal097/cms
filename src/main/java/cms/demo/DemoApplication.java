package cms.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Collections;

@SpringBootApplication
@EnableAsync
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        springApplication.setDefaultProperties(Collections.singletonMap("server.port","9999"));
        springApplication.run(args);
    }

}

package kr.ac.kookmin.wink.planlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PlanListApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanListApplication.class, args);
    }

}
package com.project.mmj.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author Neumann
 */
@SpringBootApplication
@EnableEurekaClient
public class Service1App {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Service1App.class, args);
    }
}

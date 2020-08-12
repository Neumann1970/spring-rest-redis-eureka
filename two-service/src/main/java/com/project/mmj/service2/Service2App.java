package com.project.mmj.service2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author Sourabh Sharma
 */
@SpringBootApplication
@EnableEurekaClient
public class Service2App {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Service2App.class, args);
    }
}

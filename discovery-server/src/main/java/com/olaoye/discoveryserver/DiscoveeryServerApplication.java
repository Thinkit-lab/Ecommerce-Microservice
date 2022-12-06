package com.olaoye.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveeryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveeryServerApplication.class, args);
    }
}

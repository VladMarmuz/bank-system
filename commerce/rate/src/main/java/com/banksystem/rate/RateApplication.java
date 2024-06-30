package com.banksystem.rate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RateApplication {
    public static final String API_VERSION = "api/v1/";

    public static void main(String[] args) {
        SpringApplication.run(RateApplication.class, args);
    }

}

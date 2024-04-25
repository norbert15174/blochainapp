package com.blockchain.blochainapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BlochainappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlochainappApplication.class, args);
    }

}

package com.mycompany.datagrapher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mycompany.datagrapher")
public class DataGraphApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataGraphApplication.class, args);
         
    }
}

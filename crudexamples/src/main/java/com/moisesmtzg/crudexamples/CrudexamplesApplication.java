package com.moisesmtzg.crudexamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@ServletComponentScan
@EnableRetry
public class CrudexamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudexamplesApplication.class, args);
    }
}

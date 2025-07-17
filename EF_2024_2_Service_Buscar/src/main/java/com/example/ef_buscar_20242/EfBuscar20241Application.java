package com.example.ef_buscar_20242;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.ef_buscar_20242")
public class EfBuscar20241Application {

    public static void main(String[] args) {
        SpringApplication.run(EfBuscar20241Application.class, args);
    }
}
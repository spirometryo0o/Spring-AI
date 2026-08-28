package com.example.shopping_vector;

import com.example.shopping_vector.service.DocumentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShoppingVectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingVectorApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(DocumentService documentService) {
        return args -> {
            documentService.saveSampleDocuments();
        };
    }
}
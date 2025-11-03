package com.example.ztp_lab1.steps;

import com.example.ztp_lab1.repository.ProductHistoryRepository;
import com.example.ztp_lab1.repository.ProductRepository;
import io.cucumber.java.Before;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.web.server.LocalServerPort;

@RequiredArgsConstructor
public class Hooks {

    private final ProductRepository productRepository;
    private final ProductHistoryRepository productHistoryRepository;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        io.restassured.RestAssured.port = port;
        io.restassured.RestAssured.baseURI = "http://localhost";

        productHistoryRepository.deleteAll();
        productRepository.deleteAll();
    }
}
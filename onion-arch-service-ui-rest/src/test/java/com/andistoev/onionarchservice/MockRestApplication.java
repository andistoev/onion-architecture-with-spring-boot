package com.andistoev.onionarchservice;

import com.andistoev.onionarchservice.api.ShoppingListService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootApplication
public class MockRestApplication {

    @MockBean
    public ShoppingListService shoppingListService;

    public static void main(String[] args) {
        SpringApplication.run(MockRestApplication.class, args);
    }
}

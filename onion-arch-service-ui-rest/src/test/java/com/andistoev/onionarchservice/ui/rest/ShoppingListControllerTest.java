package com.andistoev.onionarchservice.ui.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingListController.class)
class ShoppingListControllerTest {

    private static final String API_URI = "/api/1/shopping-list";

    private static final UUID MOCK_SHOPPING_LIST_ID = UUID.randomUUID();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createShoppingList() throws Exception {
        mockMvc.perform(put(API_URI + "/"))
            .andExpect(status().isOk());
    }

    @Test
    void addItemToTheShoppingList() throws Exception {
        String uri = String.format("%s/%s/item", API_URI, MOCK_SHOPPING_LIST_ID);
        mockMvc
            .perform(put(uri)
                .queryParam("productName", "milk")
                .queryParam("price", "3.8")
                .queryParam("quantity", "1")
            )
            .andExpect(status().isOk());
    }

    @Test
    void getTotalPrice() throws Exception {
        String uri = String.format("%s/%s/total-price", API_URI, MOCK_SHOPPING_LIST_ID);
        mockMvc.perform(get(uri))
            .andExpect(status().isOk());
    }
}
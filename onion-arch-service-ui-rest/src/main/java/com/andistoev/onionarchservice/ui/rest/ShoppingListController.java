package com.andistoev.onionarchservice.ui.rest;

import com.andistoev.onionarchservice.api.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/api/shopping-list")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @PutMapping("/")
    UUID createShoppingList() {
        return shoppingListService.createShoppingList();
    }

    @PutMapping("/{shoppingListId}/item")
    UUID addItemToTheShoppingList(
        @PathVariable UUID shoppingListId,
        @RequestParam String productName,
        @RequestParam Double price,
        @RequestParam Integer quantity) {

        Validate.noNullElements(Arrays.asList(shoppingListId, productName, price, quantity),
            "Invalid input parameter/-s: shoppingListId=%s, productName=%s, price=%f, quantity=%d", shoppingListId, productName, price, quantity);

        Validate.isTrue(quantity >= 1, "The quantity has to be greater or equal to 1");

        return shoppingListService.addItemToTheShoppingList(shoppingListId, productName, price, quantity);
    }

    @GetMapping("/{shoppingListId}/total-price")
    double getTotalPrice(@PathVariable UUID shoppingListId) {
        Validate.notNull(shoppingListId, "Missing mandatory input parameter: shoppingListId");
        return shoppingListService.getTotalPrice(shoppingListId);
    }
}

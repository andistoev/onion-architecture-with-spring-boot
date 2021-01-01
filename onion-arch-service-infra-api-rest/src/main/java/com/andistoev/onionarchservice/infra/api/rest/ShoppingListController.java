package com.andistoev.onionarchservice.infra.api.rest;

import com.andistoev.onionarchservice.api.ShoppingListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/1/shopping-list")
@Api(value = "API to shopping list", produces = "application/json")
@RequiredArgsConstructor
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @ApiOperation(value = "Create new shopping list", produces = "application/json")
    @PutMapping("/")
    UUID createShoppingList() {
        return shoppingListService.createShoppingList();
    }

    @ApiOperation(value = "Add new item to a shopping list", produces = "application/json")
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

    @ApiOperation(value = "Get shopping list's total price, with the shipping costs of 10 credits included (if the price is >=100 credits, then the shipping is free of charge)!", produces = "application/json")
    @GetMapping("/{shoppingListId}/total-price")
    double getTotalPrice(@PathVariable UUID shoppingListId) {
        Validate.notNull(shoppingListId, "Missing mandatory input parameter: shoppingListId");
        return shoppingListService.getTotalPrice(shoppingListId);
    }
}

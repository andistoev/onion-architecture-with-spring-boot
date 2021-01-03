package com.andistoev.onionarchservice.infra.api.rest;

import com.andistoev.onionarchservice.api.ShoppingListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.UUID;

import static com.andistoev.onionarchservice.infra.api.rest.ShoppingListController.BASE_URI;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(BASE_URI)
@Api(value = "API to shopping list", produces = "application/json")
@RequiredArgsConstructor
public class ShoppingListController {

    protected static final String BASE_URI = "/api/v1/shoppinglists";

    private final ShoppingListService shoppingListService;

    @ApiOperation(value = "Create new shopping list", produces = "application/json")
    @PostMapping("/")
    ResponseEntity<?> createShoppingList(UriComponentsBuilder uriComponentsBuilder) {
        UriComponents uriComponents = uriComponentsBuilder
            .path(BASE_URI + "/{shoppingListId}")
            .buildAndExpand(
                shoppingListService.createShoppingList()
            );

        return created(uriComponents.toUri()).build();
    }

    @ApiOperation(value = "Add new item to a shopping list", produces = "application/json")
    @PostMapping("/{shoppingListId}/items")
    ResponseEntity<?> addItemToTheShoppingList(
        UriComponentsBuilder uriComponentsBuilder,
        @PathVariable UUID shoppingListId,
        @RequestParam String productName,
        @RequestParam Double price,
        @RequestParam Integer quantity) {

        Validate.noNullElements(Arrays.asList(shoppingListId, productName, price, quantity),
            "Invalid input parameter/-s: shoppingListId=%s, productName=%s, price=%f, quantity=%d",
            shoppingListId, productName, price, quantity);

        Validate.isTrue(quantity >= 1, "The quantity has to be greater or equal to 1");

        UriComponents uriComponents = uriComponentsBuilder
            .path(BASE_URI + "/" + shoppingListId + "/items/{itemId}")
            .buildAndExpand(
                shoppingListService.addItemToTheShoppingList(shoppingListId, productName, price, quantity)
            );

        return created(uriComponents.toUri()).build();
    }

    @ApiOperation(value = "Get shopping list's total price, with the shipping costs of 10 credits included"
        +" (if the price is >=100 credits, then the shipping is free of charge)!",
        produces = "application/json")
    @GetMapping("/{shoppingListId}/totalprice")
    ResponseEntity<?> getTotalPrice(@PathVariable UUID shoppingListId) {
        Validate.notNull(shoppingListId, "Missing mandatory input parameter: shoppingListId");
        return ok().body(shoppingListService.getTotalPrice(shoppingListId));
    }
}
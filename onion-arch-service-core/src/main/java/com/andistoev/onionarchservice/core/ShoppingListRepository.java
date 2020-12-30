package com.andistoev.onionarchservice.core;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingListRepository {
    ShoppingList save(ShoppingList shoppingList);

    Optional<ShoppingList> findById(UUID id);

    default ShoppingList findByIdOrFail(UUID id) {
        return findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Item with id: <" + id + "> not found!"));
    }

    void deleteAll();
}
package com.andistoev.onionarchservice.core;

import java.util.Optional;
import java.util.UUID;

public interface ShoppingListRepository {
    ShoppingList save(ShoppingList shoppingList);

    Optional<ShoppingList> findById(UUID id);

    ShoppingList findByIdOrFail(UUID id);

    void deleteAll();
}

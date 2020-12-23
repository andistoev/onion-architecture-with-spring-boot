package com.andistoev.onionarchservice.infrastructure.jpa;

import com.andistoev.onionarchservice.core.ShoppingList;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JpaShoppingListRepository extends CrudRepository<ShoppingList, UUID> {
}

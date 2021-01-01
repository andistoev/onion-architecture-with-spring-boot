package com.andistoev.onionarchservice.infra.jpa;

import com.andistoev.onionarchservice.core.ShoppingList;
import com.andistoev.onionarchservice.core.ShoppingListRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface JpaShoppingListRepository extends CrudRepository<ShoppingList, UUID>, ShoppingListRepository {
}

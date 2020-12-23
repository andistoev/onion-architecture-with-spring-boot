package com.andistoev.onionarchservice.infrastructure.jpa;

import com.andistoev.onionarchservice.core.ShoppingList;
import com.andistoev.onionarchservice.core.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SpringShoppingListRepository implements ShoppingListRepository {

    private final JpaShoppingListRepository jpaShoppingListRepository;

    @Override
    public ShoppingList save(ShoppingList shoppingList) {
        return jpaShoppingListRepository.save(shoppingList);
    }

    @Override
    public Optional<ShoppingList> findById(UUID id) {
        return jpaShoppingListRepository.findById(id);
    }

    @Override
    public ShoppingList findByIdOrFail(UUID id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item with id: <" + id + "> not found!"));
    }

    @Override
    public void deleteAll() {
        jpaShoppingListRepository.deleteAll();
    }

}

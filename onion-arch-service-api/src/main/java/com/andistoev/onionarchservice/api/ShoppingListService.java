package com.andistoev.onionarchservice.api;

import com.andistoev.onionarchservice.core.ShoppingItem;
import com.andistoev.onionarchservice.core.ShoppingList;
import com.andistoev.onionarchservice.core.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    @Transactional
    public UUID createShoppingList(){
        ShoppingList shoppingList = ShoppingList.of();
        shoppingListRepository.save(shoppingList);
        return shoppingList.getId();
    }

    @Transactional
    public UUID addItemToTheShoppingList(UUID shoppingListId, String productName, double price, int quantity){
        ShoppingList shoppingList = shoppingListRepository.findByIdOrFail(shoppingListId);
        ShoppingItem shoppingItem = ShoppingItem.of(productName, price, quantity);
        shoppingList.addItem(shoppingItem);
        shoppingListRepository.save(shoppingList);
        return shoppingItem.getId();
    }

    @Transactional(readOnly = true)
    public double getTotalPrice(UUID shoppingListId){
        ShoppingList shoppingList = shoppingListRepository.findByIdOrFail(shoppingListId);
        return shoppingList.getTotalPrice();
    }
}

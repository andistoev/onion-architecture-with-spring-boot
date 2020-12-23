package com.andistoev.onionarchservice.api;

import com.andistoev.onionarchservice.core.ShoppingItem;
import com.andistoev.onionarchservice.core.ShoppingList;
import com.andistoev.onionarchservice.core.ShoppingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ShoppingListServiceTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;

    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() {
        shoppingListService = new ShoppingListService(shoppingListRepository);
    }

    @Test
    void createShoppingList() {
        // given
        // when
        UUID shoppingListId = shoppingListService.createShoppingList();

        // then
        assertNotNull(shoppingListId);

        verify(shoppingListRepository, times(1)).save(any());
        verifyNoMoreInteractions(shoppingListRepository);
    }

    @Test
    void addItemToTheShoppingList() {
        // given
        ShoppingList shoppingList = ShoppingList.of();
        doReturn(shoppingList).when(shoppingListRepository).findByIdOrFail(any());

        ShoppingItem milkItem = ShoppingItem.of("milk", 3.8d, 1);

        // when
        UUID shoppingItemId = shoppingListService.addItemToTheShoppingList(
            shoppingList.getId(),
            milkItem.getProductName(),
            milkItem.getPrice(),
            milkItem.getQuantity()
        );

        // then
        assertNotNull(shoppingItemId);
        ShoppingItem actualShoppingItem = shoppingList.getItem(shoppingItemId);

        assertAll(
            () -> assertEquals(milkItem.getProductName(), actualShoppingItem.getProductName()),
            () -> assertEquals(milkItem.getPrice(), actualShoppingItem.getPrice()),
            () -> assertEquals(milkItem.getQuantity(), actualShoppingItem.getQuantity())
        );

        verify(shoppingListRepository, times(1)).findByIdOrFail(any());
        verify(shoppingListRepository, times(1)).save(any());
        verifyNoMoreInteractions(shoppingListRepository);
    }

    @Test
    void getTotalPrice() {
        // given
        ShoppingList shoppingList = ShoppingList.of();
        ShoppingItem milkItem = ShoppingItem.of("milk", 3.8d, 100);
        shoppingList.addItem(milkItem);

        doReturn(shoppingList).when(shoppingListRepository).findByIdOrFail(any());

        // when
        double totalPrice = shoppingListService.getTotalPrice(shoppingList.getId());

        // then
        assertEquals(milkItem.getSubtotalPrice(), totalPrice);

        verify(shoppingListRepository, times(1)).findByIdOrFail(any());
        verifyNoMoreInteractions(shoppingListRepository);
    }
}
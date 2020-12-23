package com.andistoev.onionarchservice.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingListTest {

    private final ShoppingItem milkItem = ShoppingItem.of("milk", 1.8d, 1);
    private final ShoppingItem breadItem = ShoppingItem.of("bread", 3d, 1);

    @Test
    void testCrudOperationsOnTheShoppingItems() {
        // given
        ShoppingList shoppingList = ShoppingList.of();

        // then
        assertSizeItemsAndSubtotalPrice(shoppingList, 0, 0, 0);
        assertTrue(milkItem.getPrice() > 0);
        assertTrue(breadItem.getPrice() > 0);

        // and when
        shoppingList.addItem(milkItem);

        // then
        assertSizeItemsAndSubtotalPrice(shoppingList, 1, 1, milkItem.getPrice());

        // and when
        breadItem.setQuantity(2);
        shoppingList.addItem(breadItem);

        // then
        assertSizeItemsAndSubtotalPrice(shoppingList, 2, 3, milkItem.getPrice() + 2 * breadItem.getPrice());

        // and when
        shoppingList.removeItem(milkItem);

        // then
        assertSizeItemsAndSubtotalPrice(shoppingList, 1, 2, 2 * breadItem.getPrice());

        // and when
        breadItem.setQuantity(1);

        // then
        assertEquals(breadItem, shoppingList.getItem(breadItem.getId()));
        assertSizeItemsAndSubtotalPrice(shoppingList, 1, 1, breadItem.getPrice());
    }

    private void assertSizeItemsAndSubtotalPrice(ShoppingList shoppingList, int expectedListSize, int expectedTotalItems, double expectedSubtotalPrice) {
        assertAll(
            () -> assertEquals(expectedListSize, shoppingList.getSize()),
            () -> assertEquals(expectedTotalItems, shoppingList.getTotalItems()),
            () -> assertEquals(expectedSubtotalPrice, shoppingList.getSubtotalPrice())
        );
    }

    @Test
    void testTotalPrice() {
        // given
        ShoppingList shoppingList = ShoppingList.of();

        // then
        assertSubtotalPriceShippingCostAndTotalPrice(shoppingList, 0, 0);

        // when
        breadItem.setQuantity(33);
        shoppingList.addItem(breadItem);

        // then
        assertSubtotalPriceShippingCostAndTotalPrice(shoppingList, 33 * breadItem.getPrice(), ShoppingList.STANDARD_SHIPPING_COSTS);

        // when
        breadItem.setQuantity(34);

        // then
        assertSubtotalPriceShippingCostAndTotalPrice(shoppingList, 34 * breadItem.getPrice(), 0);
    }

    private void assertSubtotalPriceShippingCostAndTotalPrice(ShoppingList shoppingList, double expectedSubtotalPrice, double expectedShippingCosts) {
        assertAll(
            () -> assertEquals(expectedSubtotalPrice, shoppingList.getSubtotalPrice()),
            () -> assertEquals(expectedShippingCosts, shoppingList.getShippingCosts()),
            () -> assertEquals(expectedSubtotalPrice + expectedShippingCosts, shoppingList.getTotalPrice())
        );
    }
}

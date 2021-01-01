package com.andistoev.onionarchservice.infra.jpa;

import com.andistoev.onionarchservice.core.ShoppingItem;
import com.andistoev.onionarchservice.core.ShoppingList;
import com.andistoev.onionarchservice.core.ShoppingListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@DataJpaTest
class JpaShoppingListRepositoryTest {

    private final ShoppingItem milkItem = ShoppingItem.of("milk", 1.8d, 1);
    private final ShoppingItem breadItem = ShoppingItem.of("bread", 3d, 1);

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @AfterEach
    public void tearDown() {
        shoppingListRepository.deleteAll();
    }

    @Test
    public void testCrudOperationsOnShoppingList() {
        // given
        TransactionStatus tx = transactionManager.getTransaction(new DefaultTransactionDefinition());
        ShoppingList expectedShoppingList = ShoppingList.of();
        expectedShoppingList.addItem(milkItem);
        expectedShoppingList.addItem(breadItem);

        // when
        expectedShoppingList = shoppingListRepository.save(expectedShoppingList);

        // then
        assertNotNull(expectedShoppingList.getId());
        transactionManager.commit(tx);

        // and when
        TransactionStatus tx2 = transactionManager.getTransaction(new DefaultTransactionDefinition());

        ShoppingList actualShoppingList = shoppingListRepository.findByIdOrFail(expectedShoppingList.getId());

        // then
        assertEquals(expectedShoppingList, actualShoppingList);
        transactionManager.commit(tx2);
    }
}
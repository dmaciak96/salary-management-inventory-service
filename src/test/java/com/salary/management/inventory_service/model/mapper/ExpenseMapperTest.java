package com.salary.management.inventory_service.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static com.salary.management.inventory_service.ExpenseDataProvider.AMOUNT;
import static com.salary.management.inventory_service.ExpenseDataProvider.CREATED_AT;
import static com.salary.management.inventory_service.ExpenseDataProvider.NAME;
import static com.salary.management.inventory_service.ExpenseDataProvider.NEED_TO_PAY_USER_ID;
import static com.salary.management.inventory_service.ExpenseDataProvider.PAID_BY_USER_ID;
import static com.salary.management.inventory_service.ExpenseDataProvider.RESOLVED;
import static com.salary.management.inventory_service.ExpenseDataProvider.SPLIT_TYPE;
import static com.salary.management.inventory_service.ExpenseDataProvider.UPDATED_AT;
import static com.salary.management.inventory_service.ExpenseDataProvider.createExpense;
import static com.salary.management.inventory_service.ExpenseDataProvider.createExpenseDto;
import static com.salary.management.inventory_service.ExpenseDataProvider.createExpenseDtoList;
import static com.salary.management.inventory_service.ExpenseDataProvider.createExpenses;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpenseMapperTest {

    private final ExpenseMapper expenseMapper = Mappers.getMapper(ExpenseMapper.class);

    @Test
    void shouldMapToEntity() {
        var id = UUID.randomUUID();
        var result = expenseMapper.toEntity(createExpenseDto(id));

        assertEquals(id, result.getId());
        assertEquals(CREATED_AT, result.getCreatedAt());
        assertEquals(UPDATED_AT, result.getUpdatedAt());
        assertEquals(NAME, result.getName());
        assertEquals(AMOUNT, result.getAmount());
        assertEquals(PAID_BY_USER_ID, result.getPaidByUserId());
        assertEquals(NEED_TO_PAY_USER_ID, result.getNeedToPayUserId());
        assertEquals(SPLIT_TYPE, result.getSplitType());
        assertEquals(RESOLVED, result.isResolved());
        assertNull(result.getBalanceGroup());
    }

    @Test
    void shouldMapToDto() {
        var id = UUID.randomUUID();
        var result = expenseMapper.toDto(createExpense(id));

        assertEquals(id, result.getId());
        assertEquals(CREATED_AT, result.getCreatedAt());
        assertEquals(UPDATED_AT, result.getUpdatedAt());
        assertEquals(NAME, result.getName());
        assertEquals(AMOUNT, result.getAmount());
        assertEquals(PAID_BY_USER_ID, result.getPaidByUserId());
        assertEquals(NEED_TO_PAY_USER_ID, result.getNeedToPayUserId());
        assertEquals(SPLIT_TYPE, result.getSplitType());
        assertEquals(RESOLVED, result.isResolved());
    }

    @Test
    void shouldMapToEntityList() {
        var ids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        var results = expenseMapper.toEntityList(createExpenseDtoList(ids));

        results.forEach(result -> {
            assertTrue(ids.contains(result.getId()));
            assertEquals(CREATED_AT, result.getCreatedAt());
            assertEquals(UPDATED_AT, result.getUpdatedAt());
            assertEquals(NAME, result.getName());
            assertEquals(AMOUNT, result.getAmount());
            assertEquals(PAID_BY_USER_ID, result.getPaidByUserId());
            assertEquals(NEED_TO_PAY_USER_ID, result.getNeedToPayUserId());
            assertEquals(SPLIT_TYPE, result.getSplitType());
            assertEquals(RESOLVED, result.isResolved());
            assertNull(result.getBalanceGroup());
        });
    }

    @Test
    void shouldMapToDtoList() {
        var ids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        var results = expenseMapper.toDtoList(createExpenses(ids));

        results.forEach(result -> {
            assertTrue(ids.contains(result.getId()));
            assertEquals(CREATED_AT, result.getCreatedAt());
            assertEquals(UPDATED_AT, result.getUpdatedAt());
            assertEquals(NAME, result.getName());
            assertEquals(AMOUNT, result.getAmount());
            assertEquals(PAID_BY_USER_ID, result.getPaidByUserId());
            assertEquals(NEED_TO_PAY_USER_ID, result.getNeedToPayUserId());
            assertEquals(SPLIT_TYPE, result.getSplitType());
            assertEquals(RESOLVED, result.isResolved());
        });
    }
}

package com.salary.management.inventory_service;

import com.salary.management.inventory_service.model.SplitType;
import com.salary.management.inventory_service.model.dto.ExpenseDto;
import com.salary.management.inventory_service.model.entity.Expense;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ExpenseDataProvider {
    public static final Instant CREATED_AT = Instant.now();
    public static final Instant UPDATED_AT = Instant.now();
    public static final String NAME = "Test expense";
    public static final BigDecimal AMOUNT = BigDecimal.valueOf(123.212);
    public static final UUID PAID_BY_USER_ID = UUID.randomUUID();
    public static final UUID NEED_TO_PAY_USER_ID = UUID.randomUUID();
    public static final SplitType SPLIT_TYPE = SplitType.SplitBetweenGroupMembers;
    public static final boolean RESOLVED = true;

    public static Expense createExpense(UUID id) {
        return Expense.builder()
                .id(id)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .name(NAME)
                .amount(AMOUNT)
                .paidByUserId(PAID_BY_USER_ID)
                .needToPayUserId(NEED_TO_PAY_USER_ID)
                .splitType(SPLIT_TYPE)
                .resolved(RESOLVED)
                .build();

    }

    public static List<Expense> createExpenses(List<UUID> ids) {
        return ids.stream()
                .map(ExpenseDataProvider::createExpense)
                .toList();
    }

    public static ExpenseDto createExpenseDto(UUID id) {
        return ExpenseDto.builder()
                .id(id)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .name(NAME)
                .amount(AMOUNT)
                .paidByUserId(PAID_BY_USER_ID)
                .needToPayUserId(NEED_TO_PAY_USER_ID)
                .splitType(SPLIT_TYPE)
                .resolved(RESOLVED)
                .build();
    }

    public static List<ExpenseDto> createExpenseDtoList(List<UUID> ids) {
        return ids.stream()
                .map(ExpenseDataProvider::createExpenseDto)
                .toList();
    }
}

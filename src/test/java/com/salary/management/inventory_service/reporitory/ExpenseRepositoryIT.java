package com.salary.management.inventory_service.reporitory;

import com.salary.management.inventory_service.AbstractIT;
import com.salary.management.inventory_service.model.SplitType;
import com.salary.management.inventory_service.model.entity.BalanceGroup;
import com.salary.management.inventory_service.model.entity.Expense;
import com.salary.management.inventory_service.repository.ExpenseRepository;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExpenseRepositoryIT extends AbstractIT {
    private static final UUID PAID_BY_USER_ID = UUID.randomUUID();
    private static final UUID NEED_TO_PAY_USER_ID = UUID.randomUUID();
    private static final SplitType SPLIT_TYPE = SplitType.FullAmountForSingleGroupMember;
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(412.12);
    private static final String NAME = "Test Expense";

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseRepositoryIT(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Test
    void shouldSaveExpenseColumns() {
        var expenseToSave = createTestExpense();

        StepVerifier.create(expenseRepository.save(expenseToSave))
                .assertNext(savedExpense -> {
                    assertNotNull(savedExpense);
                    assertNotNull(savedExpense.getId());
                    assertNotNull(savedExpense.getCreatedAt());
                    assertNotNull(savedExpense.getUpdatedAt());
                    assertEquals(NAME, savedExpense.getName());
                    assertEquals(AMOUNT, savedExpense.getAmount());
                    assertEquals(PAID_BY_USER_ID, savedExpense.getPaidByUserId());
                    assertEquals(NEED_TO_PAY_USER_ID, savedExpense.getNeedToPayUserId());
                    assertEquals(SPLIT_TYPE, savedExpense.getSplitType());
                    assertFalse(savedExpense.isResolved());
                })
                .verifyComplete();
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        var expense = createTestExpense();
        expense.setName(null);

        StepVerifier.create(expenseRepository.save(expense))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        var expense = createTestExpense();
        expense.setName(StringUtils.EMPTY);

        StepVerifier.create(expenseRepository.save(expense))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {
        var expense = createTestExpense();
        expense.setAmount(null);

        StepVerifier.create(expenseRepository.save(expense))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenPaidByUserIdIsNull() {
        var expense = createTestExpense();
        expense.setPaidByUserId(null);

        StepVerifier.create(expenseRepository.save(expense))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    @Test
    void shouldThrowExceptionWhenSplitTypeIsNull() {
        var expense = createTestExpense();
        expense.setSplitType(null);

        StepVerifier.create(expenseRepository.save(expense))
                .expectError(ConstraintViolationException.class)
                .verify();
    }

    private Expense createTestExpense() {
        return Expense.builder()
                .name(NAME)
                .amount(AMOUNT)
                .paidByUserId(PAID_BY_USER_ID)
                .splitType(SPLIT_TYPE)
                .needToPayUserId(NEED_TO_PAY_USER_ID)
                .resolved(false)
                .balanceGroup(BalanceGroup.builder().build())
                .build();
    }
}

package com.salary.management.inventory_service.reporitory;

import com.salary.management.inventory_service.AbstractIT;
import com.salary.management.inventory_service.model.Expense;
import com.salary.management.inventory_service.model.SplitType;
import com.salary.management.inventory_service.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExpenseRepositoryIT extends AbstractIT {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseRepositoryIT(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Test
    void shouldSaveAuditableColumns() {
        var expenseToSave = Expense.builder()
                .name("Test Expense")
                .amount(BigDecimal.valueOf(412.12))
                .paidByUserId(UUID.randomUUID())
                .splitType(SplitType.SplitBetweenGroupMembers)
                .resolved(false)
                .build();

        StepVerifier.create(expenseRepository.save(expenseToSave))
                .assertNext(savedExpense -> {
                    assertNotNull(savedExpense);
                    assertNotNull(savedExpense.getId());
                    assertNotNull(savedExpense.getCreatedAt());
                    assertNotNull(savedExpense.getUpdatedAt());
                })
                .verifyComplete();
    }
}

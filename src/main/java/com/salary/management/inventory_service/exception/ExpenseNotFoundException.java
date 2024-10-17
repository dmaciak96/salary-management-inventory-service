package com.salary.management.inventory_service.exception;

import java.util.UUID;

public class ExpenseNotFoundException extends RuntimeException {
    public ExpenseNotFoundException(UUID expenseId) {
        super("Expense not found with id: " + expenseId);
    }
}

package com.salary.management.inventory_service.exception;

import java.util.UUID;

public class BalanceGroupNotFoundException extends RuntimeException {
    public BalanceGroupNotFoundException(UUID balanceGroupId) {
        super("BalanceGroup not found with id: " + balanceGroupId);
    }
}

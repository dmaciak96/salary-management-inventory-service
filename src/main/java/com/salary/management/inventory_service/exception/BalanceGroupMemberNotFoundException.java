package com.salary.management.inventory_service.exception;

import java.util.UUID;

public class BalanceGroupMemberNotFoundException extends RuntimeException {
    public BalanceGroupMemberNotFoundException(UUID balanceGroupMemberId) {
        super("GroupMember not found with id: " + balanceGroupMemberId);
    }
}

package com.salary.management.inventory_service.model.http;

import com.salary.management.inventory_service.model.SplitType;

import java.math.BigDecimal;
import java.util.UUID;

public record ExpenseHttpRequest(String name,
                                 BigDecimal amount,
                                 UUID paidByGroupMember,
                                 UUID needToPayGroupMember,
                                 SplitType splitType,
                                 boolean resolved) {
}

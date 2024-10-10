package com.salary.management.inventory_service;

import com.salary.management.inventory_service.model.dto.BalanceGroupDto;
import com.salary.management.inventory_service.model.entity.BalanceGroup;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class BalanceGroupDataProvider {
    public static final Instant CREATED_AT = Instant.now();
    public static final Instant UPDATED_AT = Instant.now();
    public static final String NAME = "Test Balance Group";
    public static final UUID OWNER_USER_ID = UUID.randomUUID();

    public record BalanceGroupCreatorIds(UUID balanceGroupId, List<UUID> expenseIds, List<UUID> groupMemberIds) {
    }

    public static BalanceGroup createBalanceGroup(BalanceGroupCreatorIds balanceGroupCreatorIds) {
        return BalanceGroup.builder()
                .id(balanceGroupCreatorIds.balanceGroupId())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .name(NAME)
                .ownerUserId(OWNER_USER_ID)
                .groupMembers(new HashSet<>(BalanceGroupMemberDataProvider.createBalanceGroupMembers(balanceGroupCreatorIds.groupMemberIds())))
                .expenses(new HashSet<>(ExpenseDataProvider.createExpenses(balanceGroupCreatorIds.expenseIds())))
                .build();
    }

    public static BalanceGroupDto createBalanceGroupDto(BalanceGroupCreatorIds balanceGroupCreatorIds) {
        return BalanceGroupDto.builder()
                .id(balanceGroupCreatorIds.balanceGroupId())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .name(NAME)
                .ownerUserId(OWNER_USER_ID)
                .groupMembers(new HashSet<>(BalanceGroupMemberDataProvider.createBalanceGroupMemberDtoList(balanceGroupCreatorIds.groupMemberIds())))
                .expenses(new HashSet<>(ExpenseDataProvider.createExpenseDtoList(balanceGroupCreatorIds.expenseIds())))
                .build();
    }

    public static List<BalanceGroup> createBalanceGroups(List<BalanceGroupCreatorIds> balanceGroupCreatorIds) {
        return balanceGroupCreatorIds.stream()
                .map(BalanceGroupDataProvider::createBalanceGroup)
                .toList();
    }

    public static List<BalanceGroupDto> createBalanceGroupDtoList(List<BalanceGroupCreatorIds> balanceGroupCreatorIds) {
        return balanceGroupCreatorIds.stream()
                .map(BalanceGroupDataProvider::createBalanceGroupDto)
                .toList();
    }
}

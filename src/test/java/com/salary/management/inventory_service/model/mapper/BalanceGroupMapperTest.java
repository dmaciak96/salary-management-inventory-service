package com.salary.management.inventory_service.model.mapper;

import com.salary.management.inventory_service.BalanceGroupDataProvider;
import com.salary.management.inventory_service.BalanceGroupMemberDataProvider;
import com.salary.management.inventory_service.ExpenseDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static com.salary.management.inventory_service.BalanceGroupDataProvider.CREATED_AT;
import static com.salary.management.inventory_service.BalanceGroupDataProvider.NAME;
import static com.salary.management.inventory_service.BalanceGroupDataProvider.OWNER_USER_ID;
import static com.salary.management.inventory_service.BalanceGroupDataProvider.UPDATED_AT;
import static com.salary.management.inventory_service.BalanceGroupDataProvider.createBalanceGroup;
import static com.salary.management.inventory_service.BalanceGroupDataProvider.createBalanceGroupDto;
import static com.salary.management.inventory_service.BalanceGroupDataProvider.createBalanceGroupDtoList;
import static com.salary.management.inventory_service.BalanceGroupDataProvider.createBalanceGroups;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.NICKNAME;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.USER_ID;
import static com.salary.management.inventory_service.ExpenseDataProvider.AMOUNT;
import static com.salary.management.inventory_service.ExpenseDataProvider.NEED_TO_PAY_USER_ID;
import static com.salary.management.inventory_service.ExpenseDataProvider.PAID_BY_USER_ID;
import static com.salary.management.inventory_service.ExpenseDataProvider.RESOLVED;
import static com.salary.management.inventory_service.ExpenseDataProvider.SPLIT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BalanceGroupMapperTest {

    private final BalanceGroupMapper balanceGroupMapper;

    @Autowired
    public BalanceGroupMapperTest(BalanceGroupMapper balanceGroupMapper) {
        this.balanceGroupMapper = balanceGroupMapper;
    }

    @Test
    void shouldMapToEntity() {
        var balanceGroupId = UUID.randomUUID();
        var expenseIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        var groupMemberIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        var balanceGroupCreator = new BalanceGroupDataProvider.BalanceGroupCreatorIds(balanceGroupId, expenseIds, groupMemberIds);

        var result = balanceGroupMapper.toEntity(createBalanceGroupDto(balanceGroupCreator));

        assertEquals(balanceGroupId, result.getId());
        assertEquals(CREATED_AT, result.getCreatedAt());
        assertEquals(UPDATED_AT, result.getUpdatedAt());
        assertEquals(NAME, result.getName());
        assertEquals(OWNER_USER_ID, result.getOwnerUserId());

        result.getGroupMembers().forEach(groupMember -> {
            assertTrue(groupMemberIds.contains(groupMember.getId()));
            assertEquals(BalanceGroupMemberDataProvider.CREATED_AT, groupMember.getCreatedAt());
            assertEquals(BalanceGroupMemberDataProvider.UPDATED_AT, groupMember.getUpdatedAt());
            assertEquals(USER_ID, groupMember.getUserId());
            assertEquals(NICKNAME, groupMember.getNickname());
        });

        result.getExpenses().forEach(expense -> {
            assertTrue(expenseIds.contains(expense.getId()));
            assertEquals(ExpenseDataProvider.CREATED_AT, expense.getCreatedAt());
            assertEquals(ExpenseDataProvider.UPDATED_AT, expense.getUpdatedAt());
            assertEquals(ExpenseDataProvider.NAME, expense.getName());
            assertEquals(AMOUNT, expense.getAmount());
            assertEquals(PAID_BY_USER_ID, expense.getPaidByUserId());
            assertEquals(NEED_TO_PAY_USER_ID, expense.getNeedToPayUserId());
            assertEquals(SPLIT_TYPE, expense.getSplitType());
            assertEquals(RESOLVED, expense.isResolved());
        });
    }

    @Test
    void shouldMapToDto() {
        var balanceGroupId = UUID.randomUUID();
        var expenseIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        var groupMemberIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        var balanceGroupCreator = new BalanceGroupDataProvider.BalanceGroupCreatorIds(balanceGroupId, expenseIds, groupMemberIds);

        var result = balanceGroupMapper.toDto(createBalanceGroup(balanceGroupCreator));

        assertEquals(balanceGroupId, result.getId());
        assertEquals(CREATED_AT, result.getCreatedAt());
        assertEquals(UPDATED_AT, result.getUpdatedAt());
        assertEquals(NAME, result.getName());
        assertEquals(OWNER_USER_ID, result.getOwnerUserId());

        result.getGroupMembers().forEach(groupMember -> {
            assertTrue(groupMemberIds.contains(groupMember.getId()));
            assertEquals(BalanceGroupMemberDataProvider.CREATED_AT, groupMember.getCreatedAt());
            assertEquals(BalanceGroupMemberDataProvider.UPDATED_AT, groupMember.getUpdatedAt());
            assertEquals(USER_ID, groupMember.getUserId());
            assertEquals(NICKNAME, groupMember.getNickname());
        });

        result.getExpenses().forEach(expense -> {
            assertTrue(expenseIds.contains(expense.getId()));
            assertEquals(ExpenseDataProvider.CREATED_AT, expense.getCreatedAt());
            assertEquals(ExpenseDataProvider.UPDATED_AT, expense.getUpdatedAt());
            assertEquals(ExpenseDataProvider.NAME, expense.getName());
            assertEquals(AMOUNT, expense.getAmount());
            assertEquals(PAID_BY_USER_ID, expense.getPaidByUserId());
            assertEquals(NEED_TO_PAY_USER_ID, expense.getNeedToPayUserId());
            assertEquals(SPLIT_TYPE, expense.getSplitType());
            assertEquals(RESOLVED, expense.isResolved());
        });
    }

    @Test
    void shouldMapToEntityList() {
        var balanceGroupCreators = List.of(
                new BalanceGroupDataProvider.BalanceGroupCreatorIds(UUID.randomUUID(),
                        List.of(UUID.randomUUID(), UUID.randomUUID()),
                        List.of(UUID.randomUUID(), UUID.randomUUID())),
                new BalanceGroupDataProvider.BalanceGroupCreatorIds(UUID.randomUUID(),
                        List.of(UUID.randomUUID(), UUID.randomUUID()),
                        List.of(UUID.randomUUID(), UUID.randomUUID()))
        );

        var expenseIds = balanceGroupCreators.stream()
                .flatMap(balanceGroupCreatorIds -> balanceGroupCreatorIds.expenseIds().stream())
                .toList();

        var groupMemberIds = balanceGroupCreators.stream()
                .flatMap(balanceGroupCreatorIds -> balanceGroupCreatorIds.groupMemberIds().stream())
                .toList();

        var balanceGroupIds = balanceGroupCreators.stream()
                .map(BalanceGroupDataProvider.BalanceGroupCreatorIds::balanceGroupId)
                .toList();

        var results = balanceGroupMapper.toEntityList(createBalanceGroupDtoList(balanceGroupCreators));

        results.forEach(result -> {
            assertTrue(balanceGroupIds.contains(result.getId()));
            assertEquals(CREATED_AT, result.getCreatedAt());
            assertEquals(UPDATED_AT, result.getUpdatedAt());
            assertEquals(NAME, result.getName());
            assertEquals(OWNER_USER_ID, result.getOwnerUserId());

            result.getGroupMembers().forEach(groupMember -> {
                assertTrue(groupMemberIds.contains(groupMember.getId()));
                assertEquals(BalanceGroupMemberDataProvider.CREATED_AT, groupMember.getCreatedAt());
                assertEquals(BalanceGroupMemberDataProvider.UPDATED_AT, groupMember.getUpdatedAt());
                assertEquals(USER_ID, groupMember.getUserId());
                assertEquals(NICKNAME, groupMember.getNickname());
            });

            result.getExpenses().forEach(expense -> {
                assertTrue(expenseIds.contains(expense.getId()));
                assertEquals(ExpenseDataProvider.CREATED_AT, expense.getCreatedAt());
                assertEquals(ExpenseDataProvider.UPDATED_AT, expense.getUpdatedAt());
                assertEquals(ExpenseDataProvider.NAME, expense.getName());
                assertEquals(AMOUNT, expense.getAmount());
                assertEquals(PAID_BY_USER_ID, expense.getPaidByUserId());
                assertEquals(NEED_TO_PAY_USER_ID, expense.getNeedToPayUserId());
                assertEquals(SPLIT_TYPE, expense.getSplitType());
                assertEquals(RESOLVED, expense.isResolved());
            });
        });
    }

    @Test
    void shouldMapToDtoList() {
        var balanceGroupCreators = List.of(
                new BalanceGroupDataProvider.BalanceGroupCreatorIds(UUID.randomUUID(),
                        List.of(UUID.randomUUID(), UUID.randomUUID()),
                        List.of(UUID.randomUUID(), UUID.randomUUID())),
                new BalanceGroupDataProvider.BalanceGroupCreatorIds(UUID.randomUUID(),
                        List.of(UUID.randomUUID(), UUID.randomUUID()),
                        List.of(UUID.randomUUID(), UUID.randomUUID()))
        );

        var expenseIds = balanceGroupCreators.stream()
                .flatMap(balanceGroupCreatorIds -> balanceGroupCreatorIds.expenseIds().stream())
                .toList();

        var groupMemberIds = balanceGroupCreators.stream()
                .flatMap(balanceGroupCreatorIds -> balanceGroupCreatorIds.groupMemberIds().stream())
                .toList();

        var balanceGroupIds = balanceGroupCreators.stream()
                .map(BalanceGroupDataProvider.BalanceGroupCreatorIds::balanceGroupId)
                .toList();

        var results = balanceGroupMapper.toDtoList(createBalanceGroups(balanceGroupCreators));

        results.forEach(result -> {
            assertTrue(balanceGroupIds.contains(result.getId()));
            assertEquals(CREATED_AT, result.getCreatedAt());
            assertEquals(UPDATED_AT, result.getUpdatedAt());
            assertEquals(NAME, result.getName());
            assertEquals(OWNER_USER_ID, result.getOwnerUserId());

            result.getGroupMembers().forEach(groupMember -> {
                assertTrue(groupMemberIds.contains(groupMember.getId()));
                assertEquals(BalanceGroupMemberDataProvider.CREATED_AT, groupMember.getCreatedAt());
                assertEquals(BalanceGroupMemberDataProvider.UPDATED_AT, groupMember.getUpdatedAt());
                assertEquals(USER_ID, groupMember.getUserId());
                assertEquals(NICKNAME, groupMember.getNickname());
            });

            result.getExpenses().forEach(expense -> {
                assertTrue(expenseIds.contains(expense.getId()));
                assertEquals(ExpenseDataProvider.CREATED_AT, expense.getCreatedAt());
                assertEquals(ExpenseDataProvider.UPDATED_AT, expense.getUpdatedAt());
                assertEquals(ExpenseDataProvider.NAME, expense.getName());
                assertEquals(AMOUNT, expense.getAmount());
                assertEquals(PAID_BY_USER_ID, expense.getPaidByUserId());
                assertEquals(NEED_TO_PAY_USER_ID, expense.getNeedToPayUserId());
                assertEquals(SPLIT_TYPE, expense.getSplitType());
                assertEquals(RESOLVED, expense.isResolved());
            });
        });
    }
}

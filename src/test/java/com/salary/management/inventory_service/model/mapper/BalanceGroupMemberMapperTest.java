package com.salary.management.inventory_service.model.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.CREATED_AT;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.NICKNAME;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.UPDATED_AT;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.USER_ID;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.createBalanceGroupMember;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.createBalanceGroupMemberDto;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.createBalanceGroupMemberDtoList;
import static com.salary.management.inventory_service.BalanceGroupMemberDataProvider.createBalanceGroupMembers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BalanceGroupMemberMapperTest {

    private final BalanceGroupMemberMapper balanceGroupMemberMapper = Mappers.getMapper(BalanceGroupMemberMapper.class);

    @Test
    void shouldMapToEntity() {
        var id = UUID.randomUUID();
        var result = balanceGroupMemberMapper.toEntity(createBalanceGroupMemberDto(id));

        assertEquals(id, result.getId());
        assertEquals(CREATED_AT, result.getCreatedAt());
        assertEquals(UPDATED_AT, result.getUpdatedAt());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(NICKNAME, result.getNickname());
        assertNull(result.getBalanceGroup());
    }

    @Test
    void shouldMapToDto() {
        var id = UUID.randomUUID();
        var result = balanceGroupMemberMapper.toDto(createBalanceGroupMember(id));

        assertEquals(id, result.getId());
        assertEquals(CREATED_AT, result.getCreatedAt());
        assertEquals(UPDATED_AT, result.getUpdatedAt());
        assertEquals(USER_ID, result.getUserId());
        assertEquals(NICKNAME, result.getNickname());
    }

    @Test
    void shouldMapToEntityList() {
        var ids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        var results = balanceGroupMemberMapper.toEntityList(createBalanceGroupMemberDtoList(ids));

        results.forEach(result -> {
            assertTrue(ids.contains(result.getId()));
            assertEquals(CREATED_AT, result.getCreatedAt());
            assertEquals(UPDATED_AT, result.getUpdatedAt());
            assertEquals(USER_ID, result.getUserId());
            assertEquals(NICKNAME, result.getNickname());
            assertNull(result.getBalanceGroup());
        });
    }

    @Test
    void shouldMapToDtoList() {
        var ids = List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        var results = balanceGroupMemberMapper.toDtoList(createBalanceGroupMembers(ids));

        results.forEach(result -> {
            assertTrue(ids.contains(result.getId()));
            assertEquals(CREATED_AT, result.getCreatedAt());
            assertEquals(UPDATED_AT, result.getUpdatedAt());
            assertEquals(USER_ID, result.getUserId());
            assertEquals(NICKNAME, result.getNickname());
        });
    }
}

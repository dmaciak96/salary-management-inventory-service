package com.salary.management.inventory_service;

import com.salary.management.inventory_service.model.dto.BalanceGroupMemberDto;
import com.salary.management.inventory_service.model.entity.BalanceGroupMember;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class BalanceGroupMemberDataProvider {
    public static final Instant CREATED_AT = Instant.now();
    public static final Instant UPDATED_AT = Instant.now();
    public static final UUID USER_ID = UUID.randomUUID();
    public static final String NICKNAME = "Test nickname";

    public static BalanceGroupMember createBalanceGroupMember(UUID id) {
        return BalanceGroupMember.builder()
                .id(id)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .userId(USER_ID)
                .nickname(NICKNAME)
                .build();
    }

    public static BalanceGroupMemberDto createBalanceGroupMemberDto(UUID id) {
        return BalanceGroupMemberDto.builder()
                .id(id)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .userId(USER_ID)
                .nickname(NICKNAME)
                .build();
    }

    public static List<BalanceGroupMember> createBalanceGroupMembers(List<UUID> ids) {
        return ids.stream()
                .map(BalanceGroupMemberDataProvider::createBalanceGroupMember)
                .toList();
    }

    public static List<BalanceGroupMemberDto> createBalanceGroupMemberDtoList(List<UUID> ids) {
        return ids.stream()
                .map(BalanceGroupMemberDataProvider::createBalanceGroupMemberDto)
                .toList();
    }
}
